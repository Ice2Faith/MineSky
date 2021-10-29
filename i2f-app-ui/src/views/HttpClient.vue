<template>
  <div>
    <h3>head</h3>
    <div class="form-item">
      <label>URL</label><input type="text" v-model="formData.url"/>
    </div>
    <div class="form-item">
      <label>Method</label><input type="text" v-model="formData.method"/>
    </div>
    <h3>Param</h3>
    <div class="form-item">
      <label>key</label><input type="text" v-model="formData.key"/>
    </div>
    <div class="form-item">
      <label>value</label><input type="text" v-model="formData.value"/>
    </div>
    <div class="form-item">
      <span class="btn-base btn-primary" @click="addParam">添加</span>
    </div>

    <div style="height: auto;">
      <h3>Params:</h3><br/>
      <ul>
        <li v-for="(item,index) in formData.datas" :key="index">
          <label>{{item.key}}</label>&emsp;:&emsp;<span>{{item.value}}</span>&emsp;&emsp;<span class="btn-base btn-small btn-danger" @click="removeItem(index)">X</span>
        </li>
      </ul>
    </div>

    <div class="form-item">
      <span class="btn-base btn-submit" @click="doSubmit">请求</span>
    </div>

    <div class="card">
      {{JSON.stringify(respData)}}
    </div>
  </div>
</template>

<script>
export default {
  name: 'HttpClient',
  props:{

  },
  data () {
    return {
      formData:{
        url:'',
        method:'get',
        datas:[],
        key:'',
        value:'',
      },
      respData:{}
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
    addParam(){
      if(!this.formData.key || this.formData.key==''){
        alert('illegal param key');
        return ;
      }
      let item={
        key:this.formData.key,
        value:this.formData.value
      }
      this.formData.datas.push(item);
      this.formData.key='';
      this.formData.value='';
    },
    removeItem(index){
      this.formData.datas.splice(index,1);
    },
    doSubmit(){
      let params={};
      this.formData.datas.forEach(item=>{
        params[item.key]=item.value;
      });
      let req=undefined;
      if(this.formData.method==='get'){
        req=this.$axios({
          url:this.formData.url,
          method:this.formData.method,
          params:params
        });
      }else{
        req=req=this.$axios({
          url:this.formData.url,
          method:this.formData.method,
          data:params
        });
      }
      req.then(resp=>{
        this.respData=resp;
      }).catch(err=>{
        this.respData=err;
      });
    }
  },
  computed:{

  },
  filters:{

  }
}
</script>

<style scoped>
  input,select{
    height: 36px;
    border: none;
    border-bottom: solid 1px lightgray;
  }
  a{
    text-decoration: none;
    color: deepskyblue;
  }
  a:hover{
    text-decoration: underline;
    color: coral;
  }
  ul,ol{
    list-style: none;
    margin: 5px 3px;
    padding: 3px 5px;
  }
  li {
    height: 24px;
    font-size: 14px;
    text-align: left;
  }
  li:hover{
    border-bottom: solid 1px lightgray;
    background-color: lightyellow;
  }
  .form-item{
    height: 48px;
    width: 90%;
    margin: 3px 5%;
    padding: 3px;
  }
  .form-item label{
    width: 25%;
    margin-right: 5%;
  }
  .form-item input,select{
    width: 70%;
    margin-left: 5%;
  }
  .btn-base{
    width: 120px;
    height: 48px;
    border: none;
    border-radius: 999px;
    padding: 5px 16px;
    font-weight: bolder;
    font-size: 22px;
  }
  .btn-small{
    width: 100px;
    height: 20px;
    padding: 3px 12px;
    font-size: 12px;
  }
  .btn-primary{
    background-color: deepskyblue;
    color: white;
  }
  .btn-info{
    background-color: white;
    border: solid 1px lightgray;
  }
  .btn-warn{
    background-color: orange;
    color: white;
  }
  .btn-danger{
    background-color: orangered;
    color: white;
  }
  .btn-submit{
    background-color: limegreen;
    color: white;
  }
  .card{
    border: solid 1px lightgray;
    padding: 5px;
    border-radius: 5px;
    margin: 5px;
  }
</style>
