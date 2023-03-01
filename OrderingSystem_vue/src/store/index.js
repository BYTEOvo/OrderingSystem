import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  namespaced: true,
  state: {
    user: {
      username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
    },
    dishCart: {
      dishCart: window.localStorage.getItem('dishCart' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]'))
    }
  },
  mutations: {
    loadDishCart (state, dishCart) {
      state.dishCart = dishCart
      window.localStorage.setItem('dishCart', JSON.stringify(dishCart))
    },
    login (state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    logout (state) {
      state.user = []
      state.dishCart = []
      window.localStorage.removeItem('user')
      window.localStorage.removeItem('dishCart')
    }
  }
})
