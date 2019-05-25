// 使用 iview 的 upload 组件时，action 不支持跨域，并且不能使用 webpack 的代理功能
// 所以使用手动上传功能，使用 Axios 来进行异步上传

handleUpload (file) {
  let fileFormData = new FormData();
  fileFormData.append('file', file);
  let requestConfig = {
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': 'Bearer ' + this.$store.state.app.currentToken
    },
  }

  this.$Notice.info({
    title: '文件正在上传',
    desc: '文件 ' + file.name + ' 正在上传。'
  })

  Axios.post('/cdPlantingPlan/plantingPlanImport', fileFormData, requestConfig).then((res) => {
    console.log(res)
    if (res.status === 200 && res.data.code == 0) {
      this.$Notice.info({
        title: '文件正在上传',
        desc: '文件 ' + file.name + ' 上传成功。'
      })
    } else {
      this.$Notice.error({
        title: '文件正在上传',
        desc: '文件 ' + file.name + ' 上传失败。'
      })
    }
  })

  // 返回 false 禁止组件自动上传
  return false;
}