import { localApiAxios } from "../utilities/http-commons";

const local = localApiAxios();
const commonConfig = {
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
  },
};

export const login = (id, password) =>
  local.post(
    `/member/login`,
    {
      id: id,
      password: password,
    },
    commonConfig
  );

export const inquireMemberInfo = (id) => {
  return local.get(`/member/${id}`);
};

export const registMember = (id, name, pwd) => {
  return local.post(
    `/member/register`,
    {
      id: id,
      username: name,
      password: pwd,
    },
    commonConfig
  );
};

// function registMember(id, name, pwd, success, fail) {
//   console.log(id, name, pwd);
//   local
//     .post(
//       `/member/register`,
//       {
//         id: id,
//         username: name,
//         password: pwd,
//       },
//       commonConfig
//     )
//     .then(success)
//     .catch(fail);
// }

// export { registMember };
