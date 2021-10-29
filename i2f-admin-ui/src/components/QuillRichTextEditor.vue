<template>
  <div>
    <quill-editor
      style="min-height: 320px;"
      v-model="content"
      :options="editorOption"
      ref="myQuillEditor">
    </quill-editor>
    <!-- 富文本内容 -->
    <div v-text="content"></div>
  </div>
</template>

<script>
import Vue from 'vue'
import VueQuillEditor from 'vue-quill-editor'
// require styles 引入样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
Vue.use(VueQuillEditor)

import { quillEditor } from 'vue-quill-editor'
import * as Quill from 'quill' //引入编辑器
import { ImageDrop } from 'quill-image-drop-module';
Quill.register('modules/imageDrop', ImageDrop);

export default {
  name: 'QuillRichTextEditor',
  props:{

  },
  data () {
    return {
      content:null,
      editorOption:{
        theme: 'snow',
        boundary: document.body,
        modules: {
          imageDrop:true,
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            [{ 'header': 1 }, { 'header': 2 }],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
            [{ 'script': 'sub' }, { 'script': 'super' }],
            [{ 'indent': '-1' }, { 'indent': '+1' }],
            [{ 'direction': 'rtl' }],
            [{ 'size': ['small', false, 'large', 'huge'] }],
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'font': [] }],
            [{ 'align': [] }],
            ['clean'],
            ['link', 'image', 'video']
          ]
        },
        placeholder: 'Insert text here ...',
        readOnly: false
      }
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
