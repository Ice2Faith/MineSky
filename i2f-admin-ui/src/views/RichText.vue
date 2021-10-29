<template>
  <div>
    <div>
      <h3>markdwon</h3>
      <MarkdownEditor></MarkdownEditor>
      <h3>quill</h3>
      <QuillRichTextEditor></QuillRichTextEditor>
    </div>
  </div>
</template>

<script>
import MarkdownEditor from "../components/MarkdownEditor";
import QuillRichTextEditor from "../components/QuillRichTextEditor";
export default {
  name: 'RichText',
  components: {QuillRichTextEditor, MarkdownEditor},
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
