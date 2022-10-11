'use strict';
const $post = document.querySelector('.post-wrap');
const category = ($post?.dataset.code)? $post.dataset.code : '';

//답글
const $replyBtn = document.getElementById('replyBtn');
$replyBtn?.addEventListener('click',e=>{
  const url = `/post/${postId.value}/reply?category=${category}`;
  location.href = url;
});
//수정
const $editBtn = document.getElementById('editBtn');
$editBtn?.addEventListener('click',e=>{
  const url = `/post/${postId.value}/edit?category=${category}`;
  location.href = url;
});
//삭제
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click',e=>{
  if(confirm('삭제하시겠습니까?')){
    const url = `/post/${postId.value}/del?category=${category}`;
    location.href = url;
  }
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  const url = `/post/list?category=${category}`;
  console.log('url='+url);
  location.href=url;
});

//전역변수
const reply = {
  postId:document.getElementById('postId'),
  pcategory:document.getElementById('pcategory'),
  email:document.getElementById('email'),
  nickname:document.getElementById('nickname'),
  rcontent:document.getElementById('rcontent'),
  //mode: null
  //mode: null
}

//입력데이터 가져오기
function getReplyData(){

  const postId = reply.postId.value;
  const pcategory = reply.pcategory.value;
  const email = reply.email.value;
  const nickname = reply.nickname.value;
  const rcontent = reply.rcontent.value;
  //변수이름을 property key, 변수값을 property value
  return {postId, pcategory, email, nickname, rcontent};
}

findAll();
//등록 클릭시
addReplyBtn.addEventListener('click', e => {
  addReply(getReplyData());
  // console.log(reply.postId, reply.pcategory, reply.nickname, reply.email, reply.rcontent);
});
//수정 클릭시
editReplyBtn.addEventListener('click', e => {
  //1)유효성 체크
  // validChk()
  //2)수정처리
  update(reply.productId.value, getInputData());
});

//삭제 클릭시
delReplyBtn.addEventListener('click', e => {
  console.log("del click");
  if(!confirm('삭제하시겠습니까?')) return;
  const rid = reply.replyId;
  deleteById(rid);
  // clearForm();
});

//등록
function addReply(reply){
const url = 'http://localhost:9080/api/reply';
fetch( url,{            //url
  method:'POST',        //http method
  headers:{             //http header
    'Content-Type':'application/json',
    'Accept':'application/json'
  },
  body: JSON.stringify(reply)   //http body      // js객체 => json포맷의 문자열
}).then(res=>res.json())
  .then(data=>{
    console.log(data)
     findAll();
     clearForm();
    })
  .catch(err=>console.log(err));
}
//목록
function findAll(){
  const pid = reply.postId.value;
  const url = `http://localhost:9080/api/reply/list/${pid}`;
  fetch( url,{            //url
    method:'GET',        //http method
    headers:{             //http header
      'Accept':'application/json'
    }
  }).then(res=>res.json())
    .then(res =>{
      // console.log(res);
      // if(res.header.rtcd == '00'){
        const result =
          res.data.map(reply =>{
              return `<div class=reply_contents><div class="reply_writer"><span id="rid">${reply.replyId}</span><span>${reply.nickname}</span><span>${reply.email}</span></div>
                      <p>${reply.rcontent}</p></div>`;
            });
        console.log(result.join(''));
        // document.getElementById('replyList').innerHTML='';
        document.getElementById('replyList').innerHTML=result.join('');

  })
  .catch(err=>console.log(err));
}

//수정
function update(id,rcontent){
  const rid = document.getElementById('reply_id').value;
  const url = `http://localhost:9080/api/reply/${rid}`;
  fetch( url,{            //url
    method:'PATCH',        //http method
    headers:{             //http header
      'Content-Type':'application/json',
      'Accept':'application/json'
    },
    // body: JSON.stringify(product)  //http body
    body: rcontent
  }).then(res=>res.json())
    .then(data=>{
      console.log(data);
      findAll();
  })
  .catch(err=>console.log(err));
}

//삭제
function deleteById(id){
  const rid = document.getElementById('rid').value;
  console.log(rid);

  const url = `http://localhost:9080/api/reply/${rid}`;
  fetch( url,{            //url
  method:'DELETE',        //http method
  headers:{             //http header
    'Accept':'application/json'
  },
  }).then(res=>res.json())
  .then(data=>{
    console.log(data);
    findAll();
  })
  .catch(err=>console.log(err));
}
//필드 clear
function clearForm(){

  reply.rcontent.value = '';
}