import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/BoardView.vue"),
      redirect: "/board/list",
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "detail/:articleNo",
          name: "board-detail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/components/board/BoardWrite.vue"),
          children: [
            {
              path: ":contentid",
              name: "board-write-review",
              component: () => import("@/components/board/BoardWrite.vue"),
            },
          ],
        },
        {
          path: "modify/:articleNo",
          name: "board-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/map",
      name: "map",
      component: () => import("@/views/MapView.vue"),
    },
    {
      path: "/member",
      name: "member",
      component: () => import("@/views/MemberView.vue"),
      children: [
        {
          path: "login",
          name: "member-login",
          component: () => import("@/components/member/MemberLogin.vue"),
        },
        {
          path: "logout",
          name: "member-logout",
          component: () => import("@/components/member/MemberLogout.vue"),
        },
        {
          path: "signup",
          name: "member-signup",
          component: () => import("@/components/member/MemberSignUp.vue"),
        },
      ],
    },
  ],
});

export default router;
