import Axios from 'axios';

download (url, params, name) {
  let token = this.$store.state.app.currentToken
  Axios({
    method: 'get',
    url: url,
    responseType: 'blob',
    params: params,
    headers: {
      'TENANT_ID': '1',
      'Authorization': 'Bearer ' + token
    },
  }).then((data) => {
    if (!data) {
      return
    }
    let url = window.URL.createObjectURL(data.data)
    let link = document.createElement('a')
    link.style.display = 'none'
    link.href = url
    link.setAttribute('download', name)
    document.body.appendChild(link)
    link.click()
  })
}