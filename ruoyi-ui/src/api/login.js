import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}


// 邮箱登录
export function loginEmail(email, code, uuid) {
  const data = {
    email,
    code,uuid
  }
  return request({
    url: '/loginEmail',
    method: 'post',
    headers: {
      isToken: false
    },
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}


// 邮箱验证码
export function sendEmailVerification(email) {
  const data = {
    email: email
  }
  return request({
    url: '/captchaEmail',
    headers: {
      isToken: false
    },
    method: 'post',
    timeout: 20000,
    data: data
  })
}