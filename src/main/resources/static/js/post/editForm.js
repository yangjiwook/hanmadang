'use strict';
//textArea => ck_editor 대체
ClassicEditor
		.create( document.querySelector( '#pcontent' ), {
		 plugin:['ListStyle','Markdown','MediaEmbed','MediaEmbedToolbar'],
			toolbar: {
				items: [
					'heading',
					'|',
					'underline',
					'bold',
					'italic',
					'link',
					'bulletedList',
					'numberedList',
					'todoList',
					'|',
					'outdent',
					'indent',
					'|',
					'imageInsert',
					'imageUpload',
					'blockQuote',
					'insertTable',
					'mediaEmbed',
					'markdown',
					'undo',
					'redo',
					'|',
					'highlight',
					'fontFamily',
					'fontColor',
					'fontBackgroundColor',
					'fontSize',
					'|',
					'htmlEmbed',
					'specialCharacters'
				]
			},
			language: 'en',
			image: {
				toolbar: [
					'imageTextAlternative',
					'imageStyle:full',
					'imageStyle:side'
				]
			},
			table: {
				contentToolbar: [
					'tableColumn',
					'tableRow',
					'mergeTableCells',
					'tableCellProperties',
					'tableProperties'
				]
			},
		})
		.then( editor => {

			window.editor = editor;
		} )
		.catch( error => {
			console.error( error );
		} );

const $post = document.querySelector('.post-wrap');
const category = ($post?.dataset.code)? $post.dataset.code : '';

//상세
const $cancelBtn = document.getElementById('cancelBtn');
$cancelBtn?.addEventListener('click',e=>{
  const url = `/post/${postId.value}?category=${category}`;
  location.href = url;
});
//저장
const $saveBtn = document.getElementById('saveBtn');
$saveBtn?.addEventListener("click", e=>{
  editForm.action = `${editForm.action}?category=${category}`;
  editForm.submit();
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  const url = `/post/list?category=${category}`;
  console.log('url='+url);
  location.href=url;
});
//첨부파일삭제
const $files = document.querySelector('.post-attach');
$files?.addEventListener('click',evt=>{
  if(evt.target.tagName != 'I') return;
  if(!confirm('삭제하시겠습니까?')) return;

  const $i = evt.target;
  const url = `/attach/${$i.dataset.fid}`;
  fetch(url,{
    method:'DELETE'
  }).then(res=>res.json())
    .then(res=>{
      if(res.rtcd == '00'){
        //첨부파일 정보 화면에서 제거
        removeAttachFileFromView(evt);
      }else{
        console.log(res.rtmsg);
      }
    })
    .catch(err=>console.log(err));
});
function removeAttachFileFromView(evt){
    const $parent = document.getElementById('attachFiles');
    const $child = evt.target.closest('.attachFile');
    $parent.removeChild($child);
}