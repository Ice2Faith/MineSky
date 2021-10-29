<template>
  <div>
    <div>
      <h3>file</h3>
      <div>
        <input type="file" @change="onFileChange">
        <a :href="fileDownData.externDownloadUrl">{{fileDownData.originalFileName}}</a>
        {{fileDownData.fileName}}<br/>
        {{fileDownData.downloadUrl}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FileUploadDownload',
  props:{

  },
  data () {
    return {
      fileDownData:{}
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

    onFileChange(file) {
      console.log('files:', file.srcElement.files);
      let param = new FormData();
      // 将得到的文件流添加到FormData对象
      param.append('file', file.srcElement.files[0]);
      this.$axios({
        url: 'ui-admin/common/api/file/upload',
        method: 'post',
        data: param,
        headers: {'Content-Type': 'multipart/form-data'}
      }).then(resp => {
        console.log('resp:', resp);
        this.fileDownData=resp.data;
      });
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
