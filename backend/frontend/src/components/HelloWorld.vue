<template>
  <div class="hello">
    <h1>{{ msg }} (1..100)</h1>
    <h2>최대 10번까지!</h2>
    <input type="radio" name="pattern" value="1" v-model="pattern" :disabled="count > 10">Step 1
    <input type="radio" name="pattern" value="2" v-model="pattern" :disabled="count > 10">Step 2
    <input type="radio" name="pattern" value="3" v-model="pattern" :disabled="count > 10">Step 3
    <input type="radio" name="pattern" value="4" v-model="pattern" :disabled="count > 10">Step 4
    <input type="radio" name="pattern" value="5" v-model="pattern" :disabled="count > 10">Step 5
    <br>
    <br>
    <input type="number" v-model="height" @keyup.enter="makeTree" placeholder="1..100" :disabled="count > 10">
    <div>
      <div class="history" style="float: left">
        <strong style="color: green">History(과거순)</strong>
        <div v-for="history in histories" :key="history.id" :style="alignChange">
          <p v-for="item in history" :key="item.id" :style="this.styles">
            {{ item }}
          </p>
          <hr>
        </div>
      </div>
      <div class="star" :style="alignChange">
        <strong style="color:red">Current Choice</strong>
        <p v-for="star in stars" :key="star.id">
          {{ star }}
        </p>
      </div>
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
  height:number = 0;
  pattern:number = 1;
  count:number = 1;
  alignChange:object = {
    'text-align': 'left',
    'margin-left': '50%',
    'float': 'left'
  }
  params:object = {
    pattern: this.pattern,
    height: this.height
  }
  histories:string[][] = []

  makeTree() {
    if (this.height < 1 || this.height > 100) {
      window.alert('1부터 100사이 숫자로 다시 적으시오');
      return;
    }

    if (!Number.isInteger(this.height)) {
      window.alert('정수로 다시 적으시오');
      return;
    }

    if (this.pattern == 3) {
      if (Number.isInteger(this.height/2)) {
        window.alert('홀수로 다시 적으시오');
        return;
      }
    }

    this.stars = [];
    this.count += 1;

    let url = "/pattern/tree/" + this.pattern + "/" + this.height
    this.params = {
      pattern: this.pattern,
      height: this.height
    }

    axios.get(url)
        .then((res) => {
          this.stars = res.data;
          this.histories.push(this.stars);
        }).catch(err => {
          window.alert("서버에 문제가 발생했습니다.");
          console.log(err.response);
    })
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
