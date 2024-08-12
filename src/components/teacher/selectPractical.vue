<template>
  <div class="exam">
    <!-- 新增的添加详情对话框 -->
    <el-dialog
      title="添加实践详情"
      :visible.sync="showAddDetailDialog"
      width="30%"
    >
      <el-form ref="detailForm" :model="detailForm" label-width="100px">
        <el-form-item label="Training ID">
          <el-input v-model="detailForm.trainingId" disabled></el-input>
        </el-form-item>

        <el-form-item label="详情内容">
          <el-input type="textarea" v-model="detailForm.title"></el-input>
        </el-form-item>
        <el-form-item label="上传文件">
          <el-upload
            action="/api/upload"
            list-type="text"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :before-upload="beforeUpload"
          >
            <el-button type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">
              只能上传PDF文件，且不超过5MB
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="submitDetail()">提交</el-button>
      </span>
    </el-dialog>
    <el-table :data="pagination.records" border>
      <el-table-column
        fixed="left"
        prop="source"
        label="来源"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="description"
        label="描述"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="institute"
        label="所属学院"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="major"
        label="所属专业"
        width="200"
      ></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column
        prop="startDate"
        label="开始日期"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="endDate"
        label="结束日期"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="totalScore"
        label="总分"
        width="120"
      ></el-table-column>
      <el-table-column prop="type" label="类型" width="120"></el-table-column>
      <el-table-column prop="tips" label="提示" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="420">
        <template slot-scope="scope">
          <el-button
            type="success"
            @click="getPracticalDetail(scope.row.practicalCode)"
          >
            详情</el-button
          >
          <el-button @click="edit(scope.row.practicalCode)" type="primary">
            编辑</el-button
          >
          <el-button
            @click="deleteRecord(scope.row.practicalCode)"
            type="danger"
            >删除</el-button
          >
          <el-button @click="addDetail(scope.row)" type="primary">
            添加详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    >
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑实践信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="来源">
            <el-input v-model="form.source"></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="form.description"></el-input>
          </el-form-item>
          <el-form-item label="所属学院">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="所属专业">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="开始日期">
            <el-col :span="11">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="form.startDate"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              ></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="结束日期">
            <el-col :span="11">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="form.endDate"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              ></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="总分">
            <el-input v-model="form.totalScore"></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-input v-model="form.type"></el-input>
          </el-form-item>
          <el-form-item label="提示">
            <el-input type="textarea" v-model="form.tips"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" style="font-size: 20px">
          取 消</el-button
        >
        <el-button type="primary" @click="submit()"> 确 定</el-button>
      </span>
    </el-dialog>

    <!-- 查询实践对话框 -->
    <el-dialog title="实践内容" :visible.sync="showPracticalDetail" width="60%">
      <span>
        <div class="content">
          <el-collapse v-model="activeName">
            <el-collapse-item class="header" name="0">
              <template slot="title">
                <div class="title">
                  <span>{{ practicalData.source }}</span
                  ><i class="header-icon el-icon-info"></i>
                  <span class="time">{{ practicalData.totalScore }}分</span>
                </div>
              </template>
              <el-collapse v-model="activeName">
                <el-collapse-item v-for="(item, index) in train" :key="index">
                  <template v-slot:title>
                    <div class="titlei">{{ item.title }}</div>
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      @click="deleteTrainItem(item.trainingId, item.id)"
                      circle
                    ></el-button>
                  </template>
                  <!-- 这里可以添加更多信息 -->
                  <div>Training ID: {{ item.trainingId }}</div>
                </el-collapse-item>
              </el-collapse>
            </el-collapse-item>
          </el-collapse>
        </div>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showPracticalDetail = false" style="font-size: 20px">
          取 消</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      form: {}, //保存点击以后当前实践的信息
      train: {},
      pagination: {
        //分页后的实践信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4, //每页条数
      },
      dialogVisible: false,
      showPracticalDetail: false, //是否展示实践
      activeName: "0", //默认打开序号
      practicalData: {
        //实践信息
      },
      practicalDetailCode: "", // 当前查看实践详情中，实践编码code
      showAddDetailDialog: false,
      detailForm: {
        trainingId: "", // 添加trainingId字段
        content: "",
      },
      filePath: "", // 用于存储上传文件的路径
    };
  },
  created() {
    this.getPracticalInfo();
  },
  methods: {
    handleCancel() {
      console.log("111");
      console.log(!this.fileUploaded);
      if (!this.fileUploaded) {
        // 如果上传了文件，调用删除接口
        this.$axios({
          url: `/api/file/delete`, // 假设删除文件的接口是这个
          method: "get",
        })
          .then((response) => {
            console.log(response);
          })
          .catch((error) => {
            console.log(error);
          });
      }
      this.showAddDetailDialog = false;
      this.detailForm = {
        trainingId: "",
        content: "",
        filePath: "",
      };
      this.fileUploaded = false; // 重置标志位
    },
    addDetail(row) {
      this.showAddDetailDialog = true;
      this.detailForm = {
        trainingId: row.trainingId, // 从传入的row数据中获取trainingId
        content: "",
      };
    },
    submitDetail() {
      this.$axios({
        url: `/api/train/`,
        method: "post",
        data: this.detailForm,
      })
        .then((response) => {
          this.$message.success("详情添加成功");
          this.showAddDetailDialog = false;
          // 可以在这里调用一个方法来刷新相关数据
        })
        .catch((error) => {
          this.$message.error("详情添加失败");
        });
    },
    handleUploadSuccess(response, file, fileList) {
      this.$message.success("File uploaded successfully");
      // 可以在这里处理文件上传成功后的逻辑，例如将文件路径保存到 detailForm 中
      this.detailForm.filePath = response.filePath; // 假设服务器返回文件路径
    },
    handleUploadError(err, file, fileList) {
      this.$message.error("File upload failed");
    },
    beforeUpload(file) {
      const isPDF = file.type === "application/pdf";
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isPDF) {
        this.$message.error("上传文件只能是PDF格式!");
      }
      if (!isLt5M) {
        this.$message.error("上传文件大小不能超过 5MB!");
      }
      return isPDF && isLt5M;
    },
    deleteTrainItem(trainingId, itemId) {
      this.trainingId = trainingId;
      this.itemId = itemId;
      console.log(this.trainingId);
      console.log(this.itemId);
      // 处理删除培训项目的方法
      this.$confirm("您确定要删除这个项目吗？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then((res) => {
          // 假设您有一个 API 端点来处理删除操作
          this.$axios(`/api/train/delete/${this.trainingId}/${this.itemId}`)
            .then((res) => {
              console.log(res);
              this.$message.success("项目删除成功");
              // 重新加载培训项目或从本地列表中移除项目
            })
            .catch((error) => {
              this.$message.error("项目删除失败");
            });
        })
        .catch(() => {
          // 处理取消操作
        });
    },
    getPracticalDetail(practicalCode) {
      this.practicalDetailCode = practicalCode;
      this.practicalData = {};
      this.showPracticalDetail = true;
      this.$axios(`/api/trains/${practicalCode}`)
        .then((res) => {
          console.log(res);
          this.train = res.data.data; // 确保这里直接赋值返回的数组
        })
        .catch((error) => {
          console.error("Failed to fetch train data:", error);
        });

      this.$axios(`/api/practical/${practicalCode}`).then((res) => {
        res.data.data.startDate = res.data.data.startDate.substr(0, 10);
        res.data.data.endDate = res.data.data.endDate.substr(0, 10);
        this.practicalData = { ...res.data.data };
      });
    },
    edit(practicalCode) {
      this.dialogVisible = true;
      this.$axios(`/api/practical/${practicalCode}`).then((res) => {
        if (res.data.code == 200) {
          this.form = res.data.data;
        }
      });
    },
    handleClose(done) {
      //关闭提醒
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    submit() {
      if (this.form.source == null || this.form.source == "") {
        this.$message({
          message: "来源不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.description == null || this.form.description == "") {
        this.$message({
          message: "描述不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.institute == null || this.form.institute == "") {
        this.$message({
          message: "所属学院不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.major == null || this.form.major == "") {
        this.$message({
          message: "所属专业不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.grade == null || this.form.grade == "") {
        this.$message({
          message: "年级不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.startDate == null || this.form.startDate == "") {
        this.$message({
          message: "开始日期不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.endDate == null || this.form.endDate == "") {
        this.$message({
          message: "结束日期不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.totalScore == null || this.form.totalScore == "") {
        this.$message({
          message: "总分不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.type == null || this.form.type == "") {
        this.$message({
          message: "类型不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.tips == null || this.form.tips == "") {
        this.$message({
          message: "提示不能为空",
          type: "error",
        });
        return;
      }
      this.dialogVisible = false;

      this.$axios({
        url: "/api/practical",
        method: "put",
        data: {
          ...this.form,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.$message({
            message: "更新成功",
            type: "success",
          });
        }
        this.getPracticalInfo();
      });
    },
    deleteRecord(practicalCode) {
      this.$confirm("确定删除该记录吗,该操作不可逆！！！", "删除提示", {
        confirmButtonText: "确定删除",
        cancelButtonText: "取消",
        type: "danger",
      })
        .then(() => {
          this.$axios({
            url: `/api/practical/${practicalCode}`,
            method: "delete",
          }).then((res) => {
            this.getPracticalInfo();
          });
        })
        .catch(() => {});
    },
    getPracticalInfo() {
      this.$axios(
        `/api/practicals/${this.pagination.current}/${this.pagination.size}`
      )
        .then((res) => {
          this.pagination = res.data.data;
        })
        .catch((error) => {});
    },
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getPracticalInfo();
    },
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getPracticalInfo();
    },
  },
};
</script>
  
  <style lang="less" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
}
</style>
  