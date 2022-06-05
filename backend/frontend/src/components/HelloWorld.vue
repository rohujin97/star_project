<template>
  <div class="hello">
    <h1> How many Star? (1..100)</h1>
    <input type="radio" name="pattern" value="1" v-model="pattern">Step 1
    <input type="radio" name="pattern" value="2" v-model="pattern">Step 2
    <input type="radio" name="pattern" value="3" v-model="pattern">Step 3
    <input type="radio" name="pattern" value="4" v-model="pattern">Step 4
    <input type="radio" name="pattern" value="5" v-model="pattern">Step 5
    <br>
    <br>
    <input type="number" v-model="height" @keyup.enter="makeTree" placeholder="1..100">
    <div style="overflow: auto;">
      <div class="history" style="float: left; width:50%;">
        <input type="checkbox" @click="allShow()"> <strong>All UP/DOWN</strong>
        <br/>
        <strong style="color:green">History</strong>
        <div v-for="(history, idx) in historiesNotCurrent.hist" :key="history.id">
          <strong style="color: green" @click="clickShow(idx)">{{ idx + 1 }}</strong>
          <transition name="slide">
            <div class="child" v-if="historiesNotCurrent.isShowing[idx]">
              <p v-for="item in history" :key="item.id" :style="this.styles">
                {{ item }}
              </p>
            </div>
          </transition>
        </div>
      </div>
      <div class="star" style="float: left; width:45%;">
        <br/>
        <strong style="color:red">Current Choice</strong>
        <p v-for="star in stars" :key="star.id">
          {{ star }}
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';
import axios from "axios";

export default class HelloWorld extends Vue {

  stars:string[] = [];
  height:number = 0;
  pattern:number = 1;
  count:number = 1;
  params:object = {
    pattern: this.pattern,
    height: this.height
  }
  histories:string[][] = []
  last:string[][] = [];
  historiesNotCurrent: {hist: string[][], isShowing: boolean[]} = {
    hist: [],
    isShowing: []
  }
  isShow: boolean = false

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
          if (this.histories.length > 11) {
            this.histories.shift();
          } else if (this.histories.length == 1) {
            return;
          }
          console.log(this.historiesNotCurrent)
          this.historiesNotCurrent.hist = this.histories.slice();
          this.historiesNotCurrent.hist = this.historiesNotCurrent.hist.splice(0, this.historiesNotCurrent.hist.length-1)
          this.historiesNotCurrent.isShowing.push(false);
        }).catch(err => {
          window.alert("서버에 문제가 발생했습니다.");
          console.log(err.response);
    })
  }

  clickShow(i: number) {
    this.historiesNotCurrent.isShowing[i] = !this.historiesNotCurrent.isShowing[i];
  }

  allShow() {
    this.isShow = !this.isShow;
    for (let i = 0; i < 10; i++) {
      this.historiesNotCurrent.isShowing[i] = this.isShow;
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
