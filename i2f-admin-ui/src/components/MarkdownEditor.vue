<template>
  <div>
    <mavon-editor v-model="value" @change="onChange"/>
    {{html}}
  </div>
</template>

<script>
import Vue from 'vue'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

export default {
  name: 'MarkdownEditor',
  props:{

  },
  data () {
    return {
      value:'',
      html:''
    }
  },
  watch:{
    '$route':{
      immediate:true,
      deep:true,
      handler:function(val,old){
        // TODO
      }
    }
  },
  created() {

  },
  methods:{
    onChange(html){
      this.html=html;
    }
  },
  computed:{

  },
  filters:{

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
