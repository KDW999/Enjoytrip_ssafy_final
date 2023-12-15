<script setup>
import { computed, ref, watch, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useAttractionStore } from "@/stores/attraction";

const emit = defineEmits(["markerClick"]);

const key = import.meta.env.VITE_KAKAOMAP_API_KEY;

const attractionStore = useAttractionStore();
const { attractions } = storeToRefs(attractionStore);

const container = ref();
let map = null;
let clusterer = null;

const markers = computed(() =>
  attractions.value.map(({ contentid, title, mapx, mapy }) => {
    const marker = new kakao.maps.Marker({
      title: title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      position: new kakao.maps.LatLng(mapy, mapx), // 마커를 표시할 위치
      image: new kakao.maps.MarkerImage(
        // 마커 이미지의 이미지 주소입니다
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
        // 마커 이미지의 이미지 크기 입니다
        new kakao.maps.Size(24, 35)
      ), // 마커 이미지
    });
    kakao.maps.event.addListener(marker, "click", () => {
      emit("markerClick", contentid);
    });

    return marker;
  })
);
const bounds = computed(() => {
  // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
  const newBounds = new kakao.maps.LatLngBounds();

  // LatLngBounds 객체에 좌표를 추가합니다
  for (const marker of markers.value) newBounds.extend(marker.getPosition());

  return newBounds;
});

const initMap = () => {
  const options = {
    center: new kakao.maps.LatLng(37.5012897, 127.0396018), // 지도의 중심좌표
    level: 5, // 지도의 확대 레벨
  };
  // 지도 객체를 등록합니다.
  // 지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
  map = new kakao.maps.Map(container.value, options);

  // 마커 클러스터러를 생성합니다
  clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 10, // 클러스터 할 최소 지도 레벨
  });
};

function displayMarker() {
  clusterer.clear();
  clusterer.addMarkers(markers.value);

  if (attractions.value.length) {
    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
    map.setBounds(bounds.value);
  } else {
    map.setCenter(new kakao.maps.LatLng(37.5012897, 127.0396018));
    map.setLevel(5);
  }
}

watch(
  attractions,
  () => { if(window?.kakao?.maps) displayMarker(); }
);

onMounted(async () => {
  if (window?.kakao?.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.src = encodeURI(
      `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false&libraries=services,clusterer,drawing`
    );
    document.head.appendChild(script);
    script.onload = () => {
      kakao.maps.load(initMap);
    };
  }
});
</script>

<template>
  <div id="map" ref="container"></div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 40dvh;
  min-height: 350px;
}
</style>
