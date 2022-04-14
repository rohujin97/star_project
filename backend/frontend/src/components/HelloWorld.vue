<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3>1...100</h3>
    <input type="radio" name="pattern" value="1" v-model="pattern">Step 1
    <input type="radio" name="pattern" value="2" v-model="pattern">Step 2
    <input type="radio" name="pattern" value="3" v-model="pattern">Step 3
    <input type="radio" name="pattern" value="4" v-model="pattern">Step 4
    <input type="radio" name="pattern" value="5" v-model="pattern">Step 5
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
import axios from "axios";

@Options({
  props: {
    msg: String,
  }
})

export default class HelloWorld extends Vue {

  stars:string[] = [];
  number:number = 0;
  pattern:number = 1;
  alignChange:object = {
    'text-align': 'left',
    'margin-left': '50%'
  }
  params:object = {
    pattern: this.pattern,
    number: this.number
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

    if (this.pattern == 2) {
      this.alignChange = {
        'text-align': 'right',
        'margin-right': '50%'
      }
    } else if (this.pattern == 3) {
      if (Number.isInteger(this.number/2)) {
        window.alert('홀수로 다시 적으시오');
        return;
      }
      this.alignChange = {
        'text-align': 'center'
      }
    }

    this.stars = [];
    let url = "/pattern/tree/" + this.pattern + "/" + this.number
    this.params = {
      pattern: this.pattern,
      number: this.number
    }

    axios.get(url)
    .then((res) => {
      console.log(res.data);
      this.stars = res.data;
    })
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
