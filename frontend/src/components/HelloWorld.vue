<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3>1...100</h3>
    <input type="radio" name="pattern" value="1" v-model="pattern">1 (easy)
    <input type="radio" name="pattern" value="2" v-model="pattern">2 (middle)
    <input type="radio" name="pattern" value="3" v-model="pattern">3 (hard)
    <br>
    <br>
    <input type="number" v-model="number" @change="makeTree" placeholder="1..100">
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
    if (this.count >= 1) {
      window.alert('오늘의 트리는 종료되었습니다.');
      return;
    }

    if (this.number < 1 || this.number > 100) {
      window.alert('1부터 100사이 숫자로 다시 적으시오');
      return;
    }

    this.stars = [];

    if (this.pattern == 1) {
      for (let i = 0; i < this.number; i++) {
        this.stars[i] = '*'.repeat(i+1);
      }
    } else if (this.pattern == 2) {
      for (let i = this.number; i >= 0; i--) {
        this.stars[i] = '*'.repeat(i);
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
