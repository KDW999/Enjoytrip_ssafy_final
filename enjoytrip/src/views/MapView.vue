<script setup>
import { computed, reactive, ref, watch, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useAreaCodeStore } from "@/stores/area-code";
import { useAttractionStore } from "@/stores/attraction";
import EmbeddedMap from "@/components/map/EmbeddedMap.vue";
import AttractionList from "@/components/map/AttractionList.vue";
import TripPlanner from "@/components/trip/TripPlanner.vue";
import "@/assets/floating-labels.css";

const areaCodeStore = useAreaCodeStore();
const attractionStore = useAttractionStore();

const planning = ref(false);

const sido = ref("");
const sigungu = ref("");
const keyword = ref("");

const contentType = ref("");

// 0: Default, enabled
// 1: Default, disabled
const defaultMessages = {
  "sido": "시/도 선택",
  "sigungu": "시/군/구 선택"
};
// 2: In progress
const progressMessages = {
  "sido": "시/도 목록 업데이트 중",
  "sigungu": "시/군/구 목록 업데이트 중"
};
// 3: Error
const errorMessages = {
  "sido": "시/도 목록 업데이트 실패",
  "sigungu": "시/군/구 목록 업데이트 실패"
};
const messages = [ defaultMessages, defaultMessages, progressMessages, errorMessages ];
const statuses = reactive({ "sido": 1, "sigungu": 1 });

const appendedMessages = computed(
  () => (
    {
      "sido": messages[statuses.sido].sido,
      "sigungu": messages[statuses.sigungu].sigungu
    }
  )
);

const areaCodeMapper = (item) => ({ "text": item.name, "value": item.code });

const { sidos, sigungus } = storeToRefs(areaCodeStore);
const { perPage, pageNo } = storeToRefs(attractionStore);

const sidoList = computed(() => sidos.value.map(areaCodeMapper));
const sigunguList = computed(() => sigungus.value.map(areaCodeMapper));

const searchMode = ref("area");
const opened = computed(
  () => (
    {
      "area": searchMode.value === "area",
      "keyword": searchMode.value === "keyword"
    }
  )
);

const { getSidos, getSigungus } = areaCodeStore;
const { getAreaBasedAttractions, getKeywordBasedAttractions, clearResults } = attractionStore;

const updateSido = () => {
    sido.value = "";
    sigungu.value = "";
    statuses.sido = 2;
    statuses.sigungu = 1;

    getSidos(
    ).then(
      () => { statuses.sido = 0; }
    ).catch(
      () => { statuses.sido = 3; }
    );
};
const updateSigungu = () => {
  sigungu.value = "";
  statuses.sigungu = 1;

  getSigungus(
    sido.value
  ).then(
    () => { statuses.sigungu = 0; }
  ).catch(
    () => { statuses.sigungu = 3; }
  );
};

const parameters = computed(
  () => (
    {
      "searchMode": searchMode.value,
      "sido": sido.value,
      "sigungu": sigungu.value,
      "keyword": keyword.value,
      "contentType": contentType.value,
      "resultRows": perPage.value
    }
  )
);
const search = (options) => {
  const p = parameters.value;
  const page = (options?.page) ? options.page : 1;
  let promise = null;

  pageNo.value = page;

  if(p.searchMode === "area") {
    if(p.sido) {
      promise = getAreaBasedAttractions(p.sido, p.sigungu, p.contentType);
    }
    else {
      clearResults();
    }
  }
  else if(p.searchMode === "keyword") {
    if(p.keyword) {
      promise = getKeywordBasedAttractions(p.keyword, p.contentType);
    }
    else {
      clearResults();
    }
  }

  if(promise) promise.then();
};

const markerFocus = ref("");
const mapMarkerClicked = (newContent) => {
  const to = document.querySelector(`#attraction-item-${encodeURIComponent(newContent)}`);
  if(to) {
    markerFocus.value = newContent;
    to.scrollIntoView({ "behavior": "smooth" });
  }
};

watch(sido, updateSigungu);
watch(parameters, search);

onMounted(
  () => { clearResults(); updateSido(); }
);
</script>

<template>
  <b-container class="mt-4">
    <b-row>
      <!-- 관광지 정보 -->
      <b-col cols="12" :md="planning ? '8' : '12'" :lg="planning ? '9': '12'" order="2" order-md="1" class="my-3">
        <!-- 페이지 제목 -->
        <b-row>
          <b-col>
            <h1>관광지 정보</h1>
          </b-col>
        </b-row>
        <!-- 관광지 조회 필터 -->
        <b-row>
          <b-col>
            <!-- 필터 종류 분류 -->
            <b-row class="mt-3">
              <b-col>
                <b-form-group>
                  <b-form-radio-group name="search-mode" v-model="searchMode">
                    <b-form-radio value="area">지역 검색</b-form-radio>
                    <b-form-radio value="keyword">관광지 검색</b-form-radio>
                  </b-form-radio-group>
                </b-form-group>
              </b-col>
            </b-row>

            <!-- 지역별 관광지 조회 -->
            <b-row v-show="opened.area">
              <!-- 시/도 선택 -->
              <b-col cols="6">
                <b-form-select name="sido" v-model="sido" :options="sidoList" :disabled="statuses.sido !== 0">
                  <template #first>
                    <b-form-select-option value="">{{ appendedMessages.sido }}</b-form-select-option>
                  </template>
                </b-form-select>
              </b-col>
              <!-- 시/군/구 선택 -->
              <b-col cols="6">
                <b-form-select name="sigungu" v-model="sigungu" :options="sigunguList" :disabled="statuses.sigungu !== 0">
                  <template #first>
                    <b-form-select-option value="">{{ appendedMessages.sigungu }}</b-form-select-option>
                  </template>
                </b-form-select>
              </b-col>
            </b-row>
            
            <!-- 키워드 기반 관광지 검색 -->
            <b-row v-show="opened.keyword">
              <b-col>
                <div class="form-label-group">
                  <b-form-input id="map-filter-keyword" type="text" name="keyword" v-model="keyword" debounce="500" />
                  <label for="map-filter-keyword">검색어</label>
                </div>
              </b-col>
            </b-row>

            <!-- 관광지 유형 선택 및 페이지 결과 수 설정 -->
            <b-row class="mt-3">
              <b-col cols="12" lg="8">
                <!-- 컨텐츠 선택 -->
                <b-form-select id="map-filter-content-type" name="content-type" v-model="contentType">
                  <option value="">관광지 유형</option>
                  <option value="12">관광지</option>
                  <option value="14">문화시설</option>
                  <option value="15">축제공연행사</option>
                  <option value="25">여행코스</option>
                  <option value="28">레포츠</option>
                  <option value="32">숙박</option>
                  <option value="38">쇼핑</option>
                  <option value="39">음식점</option>
                </b-form-select>
              </b-col>
              <b-col cols="12" lg="4" class="mt-3 mt-lg-0">
                <b-row class="g-3">
                  <b-col cols="auto">
                    <label for="map-result-rows" class="col-form-label">페이지 결과 수</label>
                  </b-col>
                  <b-col align-self="stretch">
                    <b-form-select id="map-result-rows" name="result-rows" v-model="perPage">
                      <option value="10">10</option>
                      <option value="25">25</option>
                      <option value="50">50</option>
                      <option value="75">75</option>
                      <option value="100">100</option>
                    </b-form-select>
                  </b-col>
                </b-row>
              </b-col>
            </b-row>
          </b-col>
        </b-row>

        <!-- 지도 -->
        <b-row class="my-3">
          <b-col>
            <embedded-map @marker-click="mapMarkerClicked" />
          </b-col>
        </b-row>

        <!-- 관광지 목록 -->
        <b-row>
          <b-col>
            <attraction-list :external-focus="markerFocus" @page-change="search" />
          </b-col>
        </b-row>
      </b-col>

      <!-- 여행 계획 -->
      <b-col cols="12" md="4" lg="3" order="1" order-md="2" v-show="planning" class="my-3">
        <trip-planner />
      </b-col>
    </b-row>
  </b-container>

  <b-container class="trip-plan-actions d-flex justify-content-end my-3">
    <b-row>
      <b-col>
        <b-button class="button-plan-toggle" pill variant="primary" @click="planning = !planning">여행 계획</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<style scoped>
.trip-plan-actions {
  position: sticky;
  bottom: 1.5em;
  z-index: 1040;
}
</style>
