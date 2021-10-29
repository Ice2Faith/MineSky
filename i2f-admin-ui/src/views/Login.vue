<template>
  <div>
    <div class="form-item">
      <label>账号</label><input type="text" v-model="formData.userName"/>
    </div>
    <div  class="form-item">
      <label>密码</label><input type="password" v-model="formData.password"/>
    </div>
    <div class="form-item">
      <span class="btn-base btn-primary" @click="toLogin">登录</span>
    </div>

  </div>
</template>

<script>


export default {
  name: 'Login',

  props:{

  },
  data () {
    return {
      formData:{
        userName:'',
        password:''
      },
      metas:{
        redirect:'',
      },
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
    this.$token.remove();
    if(this.$route.query.redirect && this.$route.query.redirect!=''){
      this.metas.redirect=this.$route.query.redirect;
    }
  },
  methods:{
    toLogin(){
      let form={
        userName:this.formData.userName,
        password:this.formData.password
      }
      this.$axios({
        url:'login',
        method:'get',
        params:form
      }).then(resp=>{
        console.log('login:resp:',resp);
        this.$token.set(resp.data);
        if(this.metas.redirect && this.metas.redirect!=''){
          console.log('loginRedirect');
          this.$router.push(decodeURI(this.metas.redirect));
        }else{
          this.$router.push('/main');
        }

      })
    },

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
