<template>
  <div class="hello">
    <h1> How many Star? (1..100)</h1>
    <input type="radio" name="pattern" value="1" v-model="pattern">Step 1
    <input type="radio" name="pattern" value="2" v-model="pattern">Step 2
    <input type="radio" name="pattern" value="3" v-model="pattern">Step 3
    <br/>
    <input type="radio" name="pattern" value="4" v-model="pattern">Step 4
    <input type="radio" name="pattern" value="5" v-model="pattern">Step 5
    <input type="radio" name="pattern" value="6" v-model="pattern">Step 6
    <br>
    <br>
    <input type="number" v-model="height" @keyup.enter="makeTree" placeholder="1..100">
    <div style="overflow: auto;">
      <div class="history" style="float: left; width:50%;">
        <input type="checkbox" @click="allShow()"> <strong>All UP/DOWN</strong>
        <br/>
        <strong style="color:green">History</strong>
        <div v-for="(history, idx) in historiesNotCurrent.hist" :key="history.id">
          <strong class="a" @click="clickShow(idx)">{{ idx + 1 }}</strong>
          <transition name="slide">
            <div v-if="historiesNotCurrent.isShowing[idx]">
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
        <div id="starList">
          <p v-for="star in stars" :key="star.id">
            {{ star }}
          </p>
        </div>
        <button id="btn-moreInfo" @click="moreInfo">별 더보기</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {Vue} from 'vue-class-component';
import axios from "axios";

export default class HelloWorld extends Vue {

  stars: string[] = [];
  height: number = 0;
  pattern: number = 1;
  count: number = 1;
  params: object = {
    pattern: this.pattern,
    height: this.height
  }
  histories: string[][] = []
  last: string[][] = [];
  historiesNotCurrent: { hist: string[][], isShowing: boolean[] } = {
    hist: [],
    isShowing: []
  }
  isShow: boolean = false
  fold: boolean = false

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
      if (Number.isInteger(this.height / 2)) {
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
          const button = document.getElementById("btn-moreInfo");
          let sty;

          if (button?.style) {
            sty = button.style;
          }

          // 길이 > 10 일때 버튼 생성
          if (this.stars.length > 10) {
            sty?.setProperty('display', 'block');
          } else {
            sty?.setProperty('display', 'none');
          }

          // history 최대 10개
          if (this.histories.length > 11) {
            this.histories.shift();
          } else if (this.histories.length == 1) {
            return;
          }

          this.historiesNotCurrent.hist = this.histories.slice();
          this.historiesNotCurrent.hist = this.historiesNotCurrent.hist.splice(0, this.historiesNotCurrent.hist.length - 1)
          this.historiesNotCurrent.isShowing.push(false);

        })
        .catch(err => {
          window.alert("서버에 문제가 발생했습니다.");
          console.log(err.response);
        })
  }

  moreInfo() {
    // eslint-disable-next-line no-undef
    const starList = document.getElementById("starList");
    const button = document.getElementById("btn-moreInfo");
    let text = button ?? null;

    if (!this.fold) {
      if (text?.innerText && starList?.style.maxHeight) {
        text.innerText = "별 접기";
        starList.style.maxHeight = "100%";
      }
      this.fold = true;
      window.scrollTo(0, document.body.scrollHeight);
    } else {
      if (text?.innerText && starList?.style.maxHeight) {
        text.innerText = "별 더보기";
        starList.style.maxHeight = "400px";
        this.fold = false;
      }
    }
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
#btn-moreInfo {
  display: none;
  width: 100px;
  margin: auto;
  background: #F68080;
  color: #ffffff;

  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  padding: 0.5rem 1rem;

  font-family: 'Noto Sans KR', sans-serif;
  font-size: 1rem;
  font-weight: 400;
  text-align: center;
  text-decoration: none;

  border: none;
  border-radius: 4px;

  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

  cursor: pointer;

  transition: 0.5s;
}

#starList {
  max-height: 400px;
  overflow: hidden;
}
</style>
