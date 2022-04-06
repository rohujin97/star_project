<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <div class="star">
      <p v-for="star in stars" :key="star.id">
        {{ star }}
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  props: {
    msg: String,
    numbers: Array,
  }
})
export default class HelloWorld extends Vue {

  created(): void {
    console.log(1234)
  }

  stars = ['*', '**', '***', '****', '*****'];
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.star {
  text-align: left;
  margin-left: 50%;
}
</style>
