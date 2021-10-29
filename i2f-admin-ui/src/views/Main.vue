<template>
  <div>
    <span class="moduleItem" @click="$router.push('/http/client')">HttpClient</span>
    <span class="moduleItem" @click="$router.push('/rich/text')">RichText</span>
    <span class="moduleItem" @click="$router.push('/file/upload')">FileUploadDownload</span>
    <span class="moduleItem" @click="$router.push('/files/manage')">FilesManege</span>
  </div>
</template>

<script>
export default {
  name: 'Main',
  props:{

  },
  data () {
    return {

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

  },
  computed:{

  },
  filters:{

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.moduleItem{
  width: 240px;
  height: 64px;
  text-align: center;
  border: solid 1px lightskyblue;
  border-radius: 12px;
  margin: 8px;
  padding: 12px;
}
</style>
