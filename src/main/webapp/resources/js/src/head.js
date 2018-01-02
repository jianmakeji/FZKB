

var data = { counter: 0 }
Vue.component('simple-counter', {
  template: '#head-template',
  data: function () {
    return data
  }
})
new Vue({
  el: '#example-2'
})