import CryptoJS from 'crypto-js'
import B64 from "./base64";

const AES={
  genKey(key){
    if(!key || key==''){
      return 'A1B2C3D4E5F67870';
    }
    let idx=0;
    let adlen=0;
    let klen=key.length;
    while((klen+adlen)%16!=0){
      idx=((idx+3)*7)%klen;
      key=key+key[idx];
      adlen++;
    }
    return key;
  },
  encrypt:function(data,key){
    let keys  = CryptoJS.enc.Utf8.parse(key);//Latin1 w8m31+Yy/Nw6thPsMpO5fg==
    let srcs = CryptoJS.enc.Utf8.parse(data);
    let encrypted = CryptoJS.AES.encrypt(srcs, keys, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs5});
    return encrypted.toString();
  },
  decrypt:function(data,key){
    let keys  = CryptoJS.enc.Utf8.parse(key);//Latin1 w8m31+Yy/Nw6thPsMpO5fg==
    let decrypt = CryptoJS.AES.decrypt(data, keys, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs5});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
  },
  encryptObj:function (data,key){
    console.log('en:src:',data);
    let srcs=B64.encryptObj(data);
    console.log('en:bsrc:',srcs);
    return this.encrypt(srcs,key);
  },
  decryptObj:function(data,key){
    console.log('de:src:',data);
    let srcs=this.decrypt(data,key);
    console.log('de:bsrc:',srcs);
    return B64.decryptObj(srcs);
  }
}
export default AES
