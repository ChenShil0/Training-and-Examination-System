<!--左边下拉导航栏-->
<template>
  <div id="left">
    <el-menu
      active-text-color="lightgrey"
      text-color="#000"
      :default-active="this.$route.path"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="flag"
      background-color="rgb(67, 112, 195)"
      menu-trigger="click"
      router
    >
      <el-submenu
        v-for="(item, index) in menu"
        :index="item.index"
        :key="index"
      >
        <template slot="title">
          <div class="el-item-menu left-width">
            <i class="iconfont" :class="item.icon" style="font-size: 28px"></i>
            <span slot="title" class="title"> {{ item.title }}</span>
          </div>
        </template>
        <el-menu-item-group
          v-for="(list, index1) in item.content"
          :key="index1"
        >
          <el-menu-item
            @click="handleTitle(item.index)"
            :index="list.path"
            v-if="list.item1 != null"
            style="color: white"
            ><i :class="list.icon" style="font-size: 24px; color: white"> </i>
            {{ list.item1 }}</el-menu-item
          >
          <el-menu-item
            @click="handleTitle(item.index)"
            :index="list.path"
            v-if="list.item2 != null"
            style="color: white"
            ><i :class="list.icon" style="font-size: 24px; color: white"> </i>
            {{ list.item2 }}</el-menu-item
          >
          <el-menu-item
            @click="handleTitle(item.index)"
            :index="list.path"
            v-if="list.item3 != null"
            style="color: white"
            ><i :class="list.icon" style="font-size: 24px; color: white"> </i>
            {{ list.item3 }}</el-menu-item
          >
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "mainLeft",
  data() {
    return {};
  },
  computed: mapState(["flag", "menu"]),
  created() {
    this.addData();
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    //点击标题传递参数给navigator组件
    handleTitle(index) {
      this.bus.$emit("sendIndex", index);
    },
    addData() {
      let role = this.$cookies.get("role");
      if (role == 0) {
        this.menu.push({
          index: "6",
          title: "教师管理",
          content: [
            { item1: "教师管理", path: "/teacherManage" },
            { item2: "添加教师", path: "/addTeacher" },
          ],
        });
      }
    },
  },
};
</script>

<style>
.el-menu-vertical-demo .el-submenu__title {
  overflow: hidden;
}

.left-width .iconfont {
  font-size: 18px;
  color: #fff;
}

.left-width {
  width: 213px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  min-height: 100vh;
}

#left {
  height: 900px;
  background-color: rgb(67, 112, 195);
  z-index: 0;
}

#left .el-menu-vertical-demo .title {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  margin-left: 14px;
}

.el-submenu {
  border-bottom: 1px solid #eeeeee0f !important;
}

.el-submenu__title:hover {
  background-color: #fff;
}

.el-submenu__title i {
  color: #fbfbfc !important;
}
</style>
