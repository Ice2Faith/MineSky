import { Base64 } from 'js-base64';

const B64={
  encrypt:function(data){
    return Base64.encode(data);
  },
  decrypt:function(data){
    return Base64.decode(data);
  },
  encryptObj:function(data){
    let js=JSON.stringify(data);
    return this.encrypt(js);
  },
  decryptObj:function(data){
    let js=this.decrypt(data);
    return JSON.parse(js);
  }
}

export default B64
