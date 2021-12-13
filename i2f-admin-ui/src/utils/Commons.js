
let _commons={
  firstDayOfYear:function(date){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    date.setMonth(0);
    date.setDate(1);
    return date;
  },
  firstDayOfMonth:function(date){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    date.setDate(1);
    return date;
  },
  lastDayOfYear:function(date){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    date.setMonth(0);
    date.setDate(1);
    date.setFullYear(date.getFullYear()+1);
    date.setMonth(0);
    date.setDate(1);
    date.setDate(date.getDate()-1);
    return date;
  },
  lastDayOfMonth:function (date){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    date.setDate(1);
    date.setMonth(date.getMonth()+1);
    date.setDate(1);
    date.setDate(date.getDate()-1);
    return date;
  },
  lastDayOfPreviousMonth:function(date){
    date=this.firstDayOfMonth(date);
    date.setDate(1);
    date.setDate(date.getDate()-1);
    return date;
  },
  firstDayOfNextMonth:function(date){
    date=this.firstDayOfMonth(date);
    date.setMonth(date.getMonth()+1);
    return date;
  },
  mondayOfDate:function(date){
    if(!date){
      date=new Date(0);
    }else{
      date=new Date(date);
    }
    let day=date.getDate();
    let week=date.getDay();
    date.setDate(day-week+1);
    return date;
  },
  sundayOfDate:function(date){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    let day=date.getDate();
    let week=date.getDay();
    date.setDate(day+(7-week));
    return date;
  },
  nextMonday:function(date){
    date=this.mondayOfDate(date);
    date.setDate(date.getDate()+7);
    return date;
  },
  previousSunday:function(date){
    date=this.sundayOfDate(date);
    date.setDate(date.getDate()-7);
    return date;
  },
  /**
   * 将日期date按照patten进行格式化为字符串
   * 支持的格式段：yyyy,MM,dd,HH,mm,ss,SSS,hh,y,M,d,H,m,s,S,h
   * @param date
   * @param patten
   * @returns {string}
   */
  formatDate:function(date,patten){
    if(!date){
      date=new Date();
    }else{
      date=new Date(date);
    }
    if(!patten || patten==''){
      patten='yyyy-MM-dd HH:mm:ss SSS';
    }
    let year=date.getFullYear();
    let month=date.getMonth()+1;
    let day=date.getDate();
    let hour=date.getHours();
    let min=date.getMinutes();
    let sec=date.getSeconds();
    let msec=date.getMilliseconds();

    let hour12=hour>12?(hour-12):hour;
    let isAm=hour<12;

    let ret=patten;
    ret=this.replaceAll(ret,'yyyy',this.paddingString(year,4,'0'));
    ret=this.replaceAll(ret,'MM',this.paddingString(month,2,'0'));
    ret=this.replaceAll(ret,'dd',this.paddingString(day,2,'0'));
    ret=this.replaceAll(ret,'HH',this.paddingString(hour,2,'0'));
    ret=this.replaceAll(ret,'mm',this.paddingString(min,2,'0'));
    ret=this.replaceAll(ret,'ss',this.paddingString(sec,2,'0'));
    ret=this.replaceAll(ret,'SSS',this.paddingString(msec,3,'0'));
    ret=this.replaceAll(ret,'hh',this.paddingString(hour12,2,'0'));

    ret=this.replaceAll(ret,'y',this.paddingString(year,0));
    ret=this.replaceAll(ret,'M',this.paddingString(month,0));
    ret=this.replaceAll(ret,'d',this.paddingString(day,0));
    ret=this.replaceAll(ret,'H',this.paddingString(hour,0));
    ret=this.replaceAll(ret,'m',this.paddingString(min,0));
    ret=this.replaceAll(ret,'s',this.paddingString(sec,0));
    ret=this.replaceAll(ret,'S',this.paddingString(msec,0));
    ret=this.replaceAll(ret,'h',this.paddingString(hour12,0));

    return ret;

  },
  /**
   * 支持将字符串str按照patten格式解析为日期Date对象
   * 支持的格式段：yyyy,MM,dd,HH,mm,ss,SSS
   * 也就是标准Java格式
   * @param str
   * @param patten
   * @returns {Date}
   */
  parseDate:function(str,patten){
    str=str+'';
    if(!patten){
      patten='yyyy-MM-dd HH:mm:ss SSS';
    }
    let idxYear4=patten.indexOf('yyyy');
    let idxMonth2=patten.indexOf('MM');
    let idxDay2=patten.indexOf('dd');
    let idxHour2=patten.indexOf('HH');
    let idxMin2=patten.indexOf('mm');
    let idxSec2=patten.indexOf('ss');
    let idxMsec3=patten.indexOf('SSS');
    let idxHour122=patten.indexOf('hh');

    let year=1980;
    let month=1;
    let day=1;
    let hour=0;
    let min=0;
    let sec=0;
    let msec=0;

    if(idxYear4>=0){
      year=parseInt(str.substring(idxYear4,idxYear4+4));
    }
    if(idxMonth2>=0){
      month=parseInt(str.substring(idxMonth2,idxMonth2+2));
    }
    if(idxDay2>=0){
      day=parseInt(str.substring(idxDay2,idxDay2+2));
    }
    if(idxHour2>=0){
      hour=parseInt(str.substring(idxHour2,idxHour2+2));
    }
    if(idxMin2>=0){
      min=parseInt(str.substring(idxMin2,idxMin2+2));
    }
    if(idxSec2>=0){
      sec=parseInt(str.substring(idxSec2,idxSec2+2));
    }
    if(idxMsec3>=0){
      msec=parseInt(str.substring(idxMsec3,idxMsec3+3));
    }
    return new Date(year,month-1,day,hour,min,sec,msec);
  },
  /**
   * 转换日期格式，给定原始日期串，源格式串，新格式串
   * @param str
   * @param srcPatten
   * @param dstPatten
   * @returns {string}
   */
  convertDateFmt:function(str,srcPatten,dstPatten){
    let date=this.parseDate(str,srcPatten);
    return this.formatDate(date,dstPatten);
  },
  /**
   * 转换日期为一个结构对象
   * @param date
   * @returns {{month: number, hour: number, year: number, millisecond: number, day: number, minute: number, second: number}}
   */
  toDateObj(date){
    if(!date){
      date=new Date();
    }
    let obj={
      year:date.getFullYear(),
      month:date.getMonth()+1,
      day:date.getDate(),
      hour:date.getHours(),
      minute:date.getMinutes(),
      second:date.getSeconds(),
      millisecond:date.getMilliseconds()
    };
    return obj;
  },
  /**
   * 将日期结构对象转换为日期
   * @param obj
   * @returns {Date}
   */
  parseDateObj(obj){
    if(!obj){
      return new Date();
    }
    return new Date(obj.year?obj.year:1980,
      obj.month?obj.month-1:0,
      obj.day?obj.day:undefined,
      obj.hour?obj.hour:undefined,
      obj.minute?obj.minute:undefined,
      obj.second?obj.second:undefined,
      obj.millisecond?obj.millisecond:undefined
    );
  },
  /**
   * 使用split&join方式实现replaceAll,
   * 由于部分浏览器不支持replaceAll函数
   * @param str
   * @param reg
   * @param rep
   */
  replaceAll(str,reg,rep){
    let ret='';
    let arr=str.split(reg);
    for(let i=0;i<arr.length;i++){
      if(i!=0){
        ret+=rep;
      }
      ret+=arr[i];
    }

    return ret;
  },
  /**
   * 对str字符串填充到指定长度使用pad字符串进行填充
   * pad长度不足以填充时，pad将会重复用来填充
   * @param str
   * @param len
   * @param pad
   * @returns {string}
   */
  paddingString:function(str,len,pad){
    str=str+'';
    pad=pad+'';
    len=parseInt(len);
    if(str.length>=len){
      return str;
    }
    if(pad==''){
      pad=' ';
    }
    let diffLen=len-str.length;
    let padStr='';
    let padLen=pad.length;
    let times=Math.floor(diffLen/padLen);
    for(let i=0;i<times;i++){
      padStr=padStr+pad;
    }
    let moreLen=diffLen-padStr.length;
    padStr=padStr+pad.substring(0,moreLen);
    return padStr+str;
  },
  /**
   * 清空对象的所有属性，用val进行填充属性
   * @param obj
   * @param val
   * @returns {*}
   */
  emptyObj(obj,val=''){
    if(!obj){
      return obj;
    }
    for(let attr in obj){
      obj[attr]=val;
    }
    return obj;
  },
  /**
   * 拷贝obj的在attrArray数组中指明的属性列表到新的对象
   * @param obj
   * @param attrArray
   * @returns {{}|*}
   */
  copyObj(obj,attrArray){
    if(!obj){
      return obj;
    }
    let ret={};
    let len=attrArray.length;
    for(let field in obj){
      for(let i=0;i<len;i++){
        let attr=attrArray[i];
        if(field==attr){
          ret[field]=obj[field];
          break;
        }
      }
    }
    return ret;
  },
  /**
   * 拷贝srcObj中的属性到dstObj中，仅拷贝二者共有的属性
   * @param srcObj
   * @param dstObj
   * @returns {*}
   */
  copyObj2(srcObj,dstObj){
    if(!dstObj){
      return dstObj;
    }
    if(!srcObj){
      return srcObj;
    }
    let ret=dstObj;

    for(let field in dstObj){
      for(let attr in srcObj){
        if(field==attr){
          ret[field]=srcObj[field];
          break;
        }
      }
    }
    return ret;
  },
  copyArr(arr,offset,len){
    let arrLen=arr.length;
    let ret=[];
    for(let i=offset;i<arrLen && i<(offset+len);i++){
      ret.push(arr[i]);
    }
    return ret;
  }
}

export var $commons=_commons;

let _date={};
_date.$addDay=function(date,num){
  date.setDate(date.getDate()+num);
  return date;
}
_date.$addMonth=function(date,num){
  date.setMonth(date.getMonth()+num);
  return date;
}
_date.$addYear=function(date,num){
  date.setFullYear(date.getFullYear()+num);
  return date;
}
_date.$addHour=function(date,num){
  date.setHours(date.getHours()+num);
  return date;
}
_date.$addMinute=function(date,num){
  date.setMinutes(date.getMinutes()+num);
  return date;
}
_date.$addSecond=function(date,num){
  date.setSeconds(date.getSeconds()+num);
  return date;
}
_date.$addMillSecond=function(date,num){
  date.setMilliseconds(date.getMilliseconds()+num);
  return date;
}

export var $date=_date;


let _str={};
_str.$toInt=function(str,radix=10){
  return parseInt(str,radix);
}
_str.$toFloat=function(str){
  return parseFloat(str);
}
_str.$toBoolean=function(str){
  if(str=='true'){
    return true;
  }else if(str=='false'){
    return false;
  }
  return false;
}
_str.$parseJson=function(str){
  return JSON.parse(str);
}
_str.$trunc=function(str,maxLen=-1){
  let mlen=parseInt(maxLen);
  if(mlen<=3){
    return str;
  }
  return str.substring(str,0,maxLen-3)+'...';
}
_str.$firstLower=function(str){
  if(str.length>=1){
    return str.substring(0,1).toLowerCase()+str.substring(1);
  }
  return str;
}
_str.$firstUpper=function(str){
  if(str.length>=1){
    return str.substring(0,1).toUpperCase()+str.substring(1);
  }
  return str;
}
_str.$toCamel=function(str){
  let ret='';
  let arr=str.split(/-|_/);
  for(let i=0;i<arr.length;i++){
    let item=arr[i].toLowerCase();
    if(i==0){
      ret=ret+this.$firstLower(item);
    }else{
      ret=ret+this.$firstUpper(item);
    }
  }
  return ret;
}
_str.$toPascal=function(str){
  let ret='';
  let arr=str.split(/-|_/);
  for(let i=0;i<arr.length;i++){
    let item=arr[i].toLowerCase();
    ret=ret+this.$firstUpper(item);
  }
  return ret;
}

export var $str=_str;

let _obj={};
_obj.$toJson=function(obj){
  return JSON.stringify(obj);
}

_obj.$fields=function(obj){
  let arr=[];
  for(let key in obj){
    if(obj[key] instanceof Function){
      continue;
    }
    arr.push(key);
  }
  return arr;
}
_obj.$methods=function(obj){
  let arr=[];
  for(let key in obj){
    if(obj[key] instanceof Function){
      arr.push(key);
    }
  }
  return arr;
}
/**
 * 深层次获取值，找不到不报错
 * 例如：
 * route=arg.vals[2].key
 * 那就等价于：
 * obj.arg.vals[2].key
 * @param route
 * @returns {Object}
 */
_obj.$deepVal=function(obj,route){
  if(!route || route==''){
    return obj;
  }
  let arr=route.split('.');
  let ret=obj;
  for(let i=0;i<arr.length;i++){
    if(!ret){
      break;
    }
    let cur=arr[i];
    let idx=cur.indexOf('[');
    if(idx>=0){
      let av=cur.substring(0,idx);
      let nv=cur.substring(idx+1);
      if(av!=''){
        ret=ret[av];
      }
      if(nv!=''){
        nv=nv.substring(0,nv.length-1);
        if(nv!=''){
          ret=ret[parseInt(nv)];
        }
      }
    }else{
      ret=ret[cur];
    }
  }
  return ret;
}

export var $obj=_obj;

let _dom={};

_dom.$remove=function(dom){
  let parent=dom.parentNode;
  let ret=dom;
  parent.removeChild(ret);
  return ret;
}
_dom.$style=function(dom,sylObj){
  for(let att in sylObj){
    let name=att.$toCamel();
    dom.style[name]=sylObj[att];
  }
  return dom;
}

export var $dom=_dom;

export var $tools={
  $commons:$commons,
  $date:$date,
  $str:$str,
  $obj:$obj,
  $dom:$dom
}


