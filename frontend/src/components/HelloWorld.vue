<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>1...100</p>
    <input type="number" v-model="numbers" @change="bindNumber" placeholder="1..100">
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
  }
})

export default class HelloWorld extends Vue {

  stars:String[] = [];
  numbers:Number = 0;

  bindNumber() {
    if (this.numbers < 1 || this.numbers > 100) {
      window.alert('1부터 100사이 숫자로 다시 적으시오');
      return;
    }

    this.stars = [];
    this.stars[0] = '*';
    for (let i = 1; i < this.numbers; i++) {
      this.stars[i] = this.stars[i-1].concat('*');
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.star {
  text-align: left;
  margin-left: 50%;
}
</style>
