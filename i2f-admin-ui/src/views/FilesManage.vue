<template>
  <div>
    <div>
      <h3>files</h3>
      <label>目录</label><input v-model="dir" /><label @click="getDirFiles">加载</label>
      <ul>
        <li v-for="(item,index) in files" :key="index" @click="onSelectFile(item)">{{item.dir?'+':'*'}}{{item.fileName}}</li>
      </ul>
    </div>
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
  name: 'FilesManage',
  props:{

  },
  data () {
    return {
      files:[],
      dir:'',
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
    this.getDirFiles();
  },
  methods:{
    onSelectFile(item){
      console.log('sel:item:',item);
      if(item.dir){
        if(item.path==''){
          this.dir="/"+item.fileName;
        }else{
          this.dir=item.path+'/'+item.fileName;
        }
        this.getDirFiles();
      }else{

      }
    },
    getDirFiles(){
      this.$axios({
        url:'ui-admin/common/api/files/list'+this.dir,
        method:'get',
      }).then(resp=>{
        console.log('list:',resp.data);
        this.files=resp.data;
        if(this.files.length>0){
          this.dir=this.files[0].path;
        }
      });
    },
    onFileChange(file) {
      console.log('files:', file.srcElement.files);
      let param = new FormData();
      // 将得到的文件流添加到FormData对象
      param.append('file', file.srcElement.files[0]);
      this.$axios({
        url: 'ui-admin/common/api/files/upload/'+this.dir,
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
