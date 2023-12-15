"use strict";

const app_name = "Enjoy Trip";

const content_types = {
	"관광지": 12,
	"문화시설": 14,
	"축제공연행사": 15,
	"여행코스": 25,
	"레포츠": 28,
	"숙박": 32,
	"쇼핑": 38,
	"음식점": 39
};

const keys = {
	"tourism": "xz0G8QPjEnQVQ34Kf69LAx48CsRdJK/JyOPVIAb6+X6HqEMlFLHm/fVvn8fCDNpG/a7wlutw5Vzy2bVpplElnQ=="
};

window.addEventListener(
	"load",
	() => {
		// 카카오지도
		var mapContainer = document.getElementById("map"), // 지도를 표시할 div
			mapOption = {
				center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
				level: 5, // 지도의 확대 레벨
			};

		// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);

		function displayMarker() {
			// 마커 이미지의 이미지 주소입니다
			var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

			for (var i = 0; i < positions.length; i++) {
				// 마커 이미지의 이미지 크기 입니다
				var imageSize = new kakao.maps.Size(24, 35);

				// 마커 이미지를 생성합니다
				var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

				// 마커를 생성합니다
				var marker = new kakao.maps.Marker({
					map: map, // 마커를 표시할 지도
					position: positions[i].latlng, // 마커를 표시할 위치
					title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
					image: markerImage, // 마커 이미지
				});
			}

			// 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
			map.setCenter(positions[0].latlng);
		}

		function moveCenter(lat, lng) {
			map.setCenter(new kakao.maps.LatLng(lat, lng));
		}

		function create_option(value, description)
		{
			let option = document.createElement("option");
			option.value = value;
			option.appendChild(document.createTextNode(description));

			return option;
		}

		var positions; // marker 배열.
		function makeList(data) {
			console.log(data);
			document.querySelector("table").setAttribute("style", "display: ;");
			let trips = data.response.body.items.item;
			let tripList = ``;
			positions = [];
			if(!trips) return;
			trips.forEach((area) => {
			tripList += `
				<tr onclick="moveCenter(` + area.mapy + `, ` + area.mapx + `);">
				<td><img src="` + area.firstimage + `" width="100px"></td>
				<td>` + area.title + `</td>
				<td>` + area.addr1 + ` ` + area.addr2 + `</td>
				<td>` + area.mapy + `</td>
				<td>` + area.mapx + `</td>
				</tr>
			`;

			let markerInfo = {
				title: area.title,
				latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
			};
			positions.push(markerInfo);
			});
			document.getElementById("trip-list").innerHTML = tripList;
			displayMarker();
		}

		// 자주 쓰일 element 미리 받아오기
		const filter_form = document.querySelector("#map-filter");
		const keyword_form = document.querySelector("#map-keyword");
		const content_type_form = document.querySelector("#map-content-type");
		const filter_elements = {
			"area": filter_form.querySelector("#map-filter-area"),
			"sigungu": filter_form.querySelector("#map-filter-sigungu"),
			"content_type": content_type_form.querySelector("#map-filter-content-type"),
			"search": keyword_form.querySelector("#map-filter-search")
		};

		let reset_filter = {
			"area": (placeholder) => {
				let area = filter_elements.area;

				area.disabled = true;
				while(area.lastChild) area.removeChild(area.lastChild);

				if(placeholder)
				{
					let loading_option = create_option("", placeholder);
					loading_option.setAttribute("selected", "selected");
					area.appendChild(loading_option);
				}
			},
			"sigungu": (placeholder) => {
				let sigungu = filter_elements.sigungu;

				sigungu.disabled = true;
				while(sigungu.lastChild) sigungu.removeChild(sigungu.lastChild);

				if(placeholder)
				{
					let loading_option = create_option("", placeholder);
					loading_option.setAttribute("selected", "selected");
					sigungu.appendChild(loading_option);
				}
			}
		};
		
		// 입력값에 맞게 <select> 업데이트
		let update_filter = {
			// 시/도 목록 업데이트
			"area": () => {
				filter_elements.area.disabled = true;
				filter_elements.sigungu.disabled = true;

				let area = filter_elements.area;
				let sigungu = filter_elements.sigungu;
				
				reset_filter.area("시/도 목록 업데이트 중");
				reset_filter.sigungu("시/군/구 선택");

				const parameters = new URLSearchParams(
					{
						"serviceKey": keys.tourism,
						"MobileOS": "ETC",
						"MobileApp": app_name,
						"_type": "json",
						"numOfRows": "32",
						"pageNo": "1"
					}
				);

				fetch(
					"https://apis.data.go.kr/B551011/KorService1/areaCode1?" + parameters.toString()
				).then(
					(response) => {
						if(!response.ok)
							throw new Error("시/도 목록 업데이트 실패");

						return response.text();
					}
				).then(
					(text) => {
						const data = JSON.parse(text);

						if(data.response.header.resultMsg !== "OK")
							throw new Error("시/도 목록 업데이트 실패");

						let count = 0;
						reset_filter.area("시/도 선택");

						data.response.body.items.item.forEach(
							(item) => {
								++count;
								area.appendChild(create_option(item.code, item.name));
							}
						);

						if(count) area.disabled = false;
					}
				).catch(
					() => reset_filter.area("시/도 목록 업데이트 실패")
				);
			},

			// 시/군/구 목록 업데이트
			"sigungu": (area) => {
				filter_elements.sigungu.disabled = true;

				let sigungu = filter_elements.sigungu;
				
				reset_filter.sigungu("시/군/구 목록 업데이트 중");

				const parameters = new URLSearchParams(
					{
						"serviceKey": keys.tourism,
						"MobileOS": "ETC",
						"MobileApp": app_name,
						"areaCode": area,
						"_type": "json",
						"numOfRows": "128",
						"pageNo": "1"
					}
				);

				fetch(
					"https://apis.data.go.kr/B551011/KorService1/areaCode1?" + parameters.toString()
				).then(
					(response) => {
						if(!response.ok)
							throw new Error("시/군/구 목록 업데이트 실패");

						return response.text();
					}
				).then(
					(text) => {
						const data = JSON.parse(text);

						if(data.response.header.resultMsg !== "OK")
							throw new Error("시/군/구 목록 업데이트 실패");

						let count = 0;
						reset_filter.sigungu("시/군/구 선택");

						data.response.body.items.item.forEach(
							(item) => {
								++count;
								sigungu.appendChild(create_option(item.code, item.name));
							}
						);

						if(count) sigungu.disabled = false;
					}
				).catch(
					() => reset_filter.sigungu("시/군/구 목록 업데이트 실패")
				);
			}
		};

		// 지역 목록 업데이트
		update_filter.area();

		function refresh_result(keyword)
		{
			// 검색 버튼을 누르면..
			// 지역, 유형, 검색어 얻기.
			// 위 데이터를 가지고 공공데이터에 요청.
			// 받은 데이터를 이용하여 화면 구성.
			let baseUrl = "https://apis.data.go.kr/B551011/KorService1/searchKeyword1?";

			let parameters = {
				"serviceKey": keys.tourism,
				"MobileOS": "ETC",
				"MobileApp": app_name,
				"_type": "json",
				"numOfRows": "1000"
			};

			let contentTypeId = filter_elements.content_type.value;
			if (parseInt(contentTypeId)) parameters.contentTypeId = contentTypeId;

			if(typeof keyword === "undefined")
			{
				baseUrl = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?";

				let areaCode = filter_elements.area.value;
				let sigunguCode = filter_elements.sigungu.value;

				if (parseInt(areaCode)) parameters.areaCode = areaCode;
				if (parseInt(sigunguCode)) parameters.sigunguCode = sigunguCode;
			}
			else {
				if(!keyword) return;
				parameters.keyword = keyword;
			}

			let searchUrl = baseUrl + new URLSearchParams(parameters).toString();

			fetch(searchUrl)
			.then((response) => response.json())
			.then((data) => makeList(data));
		}

		filter_elements.area.addEventListener(
			"change",
			(event) => {
				const value = event.target.value;
				if(value) update_filter.sigungu(value);
				else reset_filter.sigungu("시/군/구 선택");
				refresh_result();
			}
		);
		filter_elements.sigungu.addEventListener(
			"change",
			() => refresh_result()
		);

		let search_timer = null;
		filter_elements.search.addEventListener(
			"input",
			() => {
				clearTimeout(search_timer);
				search_timer = setTimeout(
					() => {
						refresh_result(filter_elements.search.value);
						clearTimeout(search_timer);
					},
					500
				);
			}
		);

		let search_mode = "category";

		filter_elements.content_type.addEventListener(
			"change",
			() => {
				console.log(search_mode, filter_elements.search.value);
				if(search_mode === "keyword") refresh_result(filter_elements.search.value);
				else refresh_result();
			}
		);

		document.querySelectorAll(
			"input[type=radio][name=search-mode]"
		).forEach(
			(radio) => {
				radio.addEventListener(
					"change",
					() => {
						search_mode = radio.value;
						const set = (search_mode === "keyword");
			
						filter_form.reset();
						keyword_form.reset();
			
						filter_elements.area.disabled = set;
						filter_elements.sigungu.disabled = set;
			
						filter_elements.search.disabled = !set;
			
						if(!set) update_filter.area();
					}
				);
			}
		);
	}
);
