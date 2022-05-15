<template>
  <div class="hello">
    <h1>{{ msg }} (1..100)</h1>
    <input type="radio" name="pattern" value="1" v-model="pattern">Step 1
    <input type="radio" name="pattern" value="2" v-model="pattern">Step 2
    <input type="radio" name="pattern" value="3" v-model="pattern">Step 3
    <input type="radio" name="pattern" value="4" v-model="pattern">Step 4
    <input type="radio" name="pattern" value="5" v-model="pattern">Step 5
    <br>
    <br>
    <input type="number" v-model="height" @keyup.enter="makeTree" placeholder="1..100">
    <div>
      <div class="history" style="float: left; width:50%">
        <strong style="color:green">History</strong>
        <div v-for="(history, idx) in historiesNotCurrent" :key="history.id" >
            <strong style="color: green" @click="isShow = !isShow">{{ idx + 1 }}</strong>
            <transition name="slide">
              <div class="child" v-if="isShow">
                <p v-for="item in history" :key="item.id" :style="this.styles">
                  {{ item }}
                </p>
              </div>
            </transition>
        </div>
      </div>
      <div class="star" style="float: left;">
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
  params:object = {
    pattern: this.pattern,
    height: this.height
  }
  isShow:boolean = false
  histories:string[][] = []
  historiesNotCurrent:string[][] = []
  last:string[][] = []

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
          this.historiesNotCurrent = this.histories.slice();
          this.historiesNotCurrent = this.historiesNotCurrent.splice(0, this.historiesNotCurrent.length-1)
          console.log(this.histories)
          console.log(this.historiesNotCurrent)
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
