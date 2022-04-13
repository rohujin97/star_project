<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3>1...100</h3>
    <input type="radio" name="pattern" value="1" v-model="pattern" :disabled="count == 1">1 (easy)
    <input type="radio" name="pattern" value="2" v-model="pattern" :disabled="count == 1">2 (middle)
    <input type="radio" name="pattern" value="3" v-model="pattern" :disabled="count == 1">3 (hard)
    <br>
    <br>
    <input type="number" v-model="number" @change="makeTree" :disabled="count == 1" placeholder="1..100">
    <div class="star" :style="alignChange">
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

  stars:string[] = [];
  number:number = 0;
  pattern:number = 1;
  count:number = 0;
  alignChange:object = {
    'text-align': 'left',
    'margin-left': '50%'
  }

  makeTree() {
    if (this.number < 1 || this.number > 100) {
      window.alert('1부터 100사이 숫자로 다시 적으시오');
      return;
    }

    if (!Number.isInteger(this.number)) {
      window.alert('정수로 다시 적으시오');
      return;
    }

    this.stars = [];

    if (this.pattern == 1) {
      for (let i = 0; i < this.number; i++) {
        this.stars[i] = '*'.repeat(i+1);
      }
    } else if (this.pattern == 2) {
      let j = this.number;
      for (let i = 0; i < this.number; i++, j--) {
        this.stars[i] = '*'.repeat(j);
      }
      this.alignChange = {
        'text-align': 'right',
        'margin-right': '50%'
      }
    } else {
      if (this.number % 2 == 0) {
        window.alert('홀수 숫자로 다시 적으시오');
        return;
      }

      for (let i = 0; i < this.number/2; i++) {
        this.stars[i] = '*'.repeat(i+1);
      }

      let j = 1
      for (let i = Math.floor(this.number/2)+1; i < this.number; i++, j++) {
        this.stars[i] = this.stars[i-2*j];
      }
      this.alignChange = {
        'text-align': 'center'
      }
    }
    this.count++;
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
