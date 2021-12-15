# 兼容IE：
## 预安装依赖：
```bash
npm install --save babel-polyfill
npm install es6-promise
```

## main.js 最开头添加如下三行
```js
import 'babel-polyfill'
import promise from 'es6-promise'
promise.polyfill()
```

## webpack.base.conf.js
- 修改module.export的entry字段为
- 原始的应该是
```js
entry: {
    app: './src/main.js'
  }
```
- 修改后
```js
entry: {
    app: ['babel-polyfill','./src/main.js']
  }
```

## 部分依赖问题
- 新建文件在项目根目录下
```bash
vue.config.js
```
- 写入以下内容
```js
module.exports = {
  transpileDependencies: ['crypto-js','js-base64']
}
```

## crypto-js 版本导致在IE中报错，缺少)括号
- 在package.json中替换版本，重新npm install
- 4.0.0以上版本有问题，换为4.0.0即可
```js
"crypto-js": "4.0.0",
```


## js-base64版本导致在IE中报错，缺少)括号
- 在package.json中替换版本，重新npm install
```js
"js-base64": "2.6.2",
```


## socket-js 报错
- 找到文件：
```bash
/node_modules/sockjs-client/dist/sockjs.js
```
- 定位行：
```bash
1605行
```
- 注释此行内容
```js
// self.xhr.send(payload);
```
