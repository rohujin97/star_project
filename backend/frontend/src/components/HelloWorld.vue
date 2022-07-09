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
    <div class="all">
      <div class="history">
        <input type="checkbox" @click="allShow()"> <strong>All UP/DOWN</strong>
        <br/>
        <strong style="color:green">History</strong>
        <div v-for="(history, idx) in historiesNotCurrent.hist" :key="history.id" ref="parentList">
          <strong @click="clickShow(idx)">{{ idx + 1 }}</strong>
          <transition name="slide">
            <div class="historyList" v-if="historiesNotCurrent.isShowing[idx]" ref="historyList">
              <p v-for="item in history" ref="hList" :key="item.id">
                {{ item }}
              </p>
            </div>
          </transition>
        </div>
      </div>
      <div class="star">
        <br/>
        <strong style="color:red">Current Choice</strong>
        <button id="btn-moreInfo" ref="starInfo" @click="moreInfo">별 더보기</button>
        <div class="starList" ref="starList">
          <p v-for="star in stars" class="pList" ref="pList" :key="star.id">
            {{ star }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';
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
  historiesNotCurrent: { hist: string[][], isShowing: boolean[], pattern: number[] } = {
    hist: [],
    isShowing: [],
    pattern: []
  }
  isShow: boolean = false
  fold: boolean = false
  foldList: string[] = [];

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
          let button: any = this.$refs.starInfo;

          // 길이 > 10 일때 버튼 생성
          if (this.stars.length > 10) {
            button.style.display = 'block';
          } else {
            button.style.display = 'none';
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
          this.historiesNotCurrent.pattern.push(this.pattern);
          this.foldList.push('none');
        })
        .catch(err => {
          window.alert("서버에 문제가 발생했습니다.");
          console.log(err.response);
        })
        .then(() => {
          if (this.pattern == 1 || this.pattern == 6) {
            let pList: any = this.$refs.pList;
            for (let pListKey in pList) {
              pList[pListKey].style.width = "fit-content";
            }
          }
        })
  }

  moreInfo() {
    let list: any = this.$refs.starList;
    let button: any = this.$refs.starInfo;
    let starList: any = this.$refs.starList;

    if (!this.fold) {
      button.innerText = "별 접기";
      list.style.maxHeight = "100%";
      starList.style.overflowX = "auto";
      this.fold = true;
    } else {
      button.innerText = "별 더보기";
      list.style.maxHeight = "400px";
      starList.style.overflowX = "hidden";
      this.fold = false;
    }
  }

  clickShow(i: number) {
    this.historiesNotCurrent.isShowing[i] = !this.historiesNotCurrent.isShowing[i];
    let button: any = this.$refs.historyInfo;

    if (this.historiesNotCurrent.isShowing[i] == true) {
      // 길이 > 10 일때 버튼 생성
      if (this.historiesNotCurrent.hist[i].length > 10) {
        button[i].style.display = 'block';

      } else {
        button[i].style.display = 'none';
      }
    } else {
      button[i].style.display = 'none';
    }

    setTimeout(() => {
      let pattern = this.historiesNotCurrent.pattern[i];
      if (pattern == 1 || pattern == 6) {
        let hList: any = this.$refs.hList;
        for (let hListKey in hList) {
          hList[hListKey].style.width = "fit-content";
        }
      }

    },10);
  }


  allShow() {
    this.isShow = !this.isShow;
    for (let i = 0; i < 10; i++) {
      this.historiesNotCurrent.isShowing[i] = this.isShow;
    }

  }

}
</script>

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

.historyList {
  max-height: 400px;
  overflow: auto;
  margin: 0;
}

.starList {
  max-height: 400px;
  overflow: hidden;
}

.history {
  background-color: lightgreen;
  margin: 10px 0 0 20%;
  float: left;
  width: 400px;
  overflow-x: auto;
}

.star {
  background-color: #F68080;
  width: 400px;
  margin: 10px 0 0 25px;
  float: left;
  overflow-x: auto;
}

.all {
  white-space: nowrap;
  overflow: auto;
}

</style>
