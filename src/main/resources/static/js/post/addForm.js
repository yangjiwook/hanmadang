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

// const $post = document.querySelector('.post-wrap');
// const category = ($post?.dataset.code)? $post.dataset.code : '';

//등록
const $writeBtn = document.getElementById('writeBtn');
$writeBtn?.addEventListener("click", e=>{
  writeForm.submit();
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener("click",e=>{
//	const category = getElementById('category').value;
  const url = `/post/list?category=${category}`;
  location.href = url;
});


dDay.addEventListener("change", e => {
	const date = document.getElementById('dDay').value;
	findListByDate(date);
	console.log();
	// const selectedEvent = document.getElementById('sawEvent').value;
	// document.getElementById('titile').value = selectedEvent;
});

sawEventSelect.addEventListener("change", e => {
//	console.log("제목선택!!")
	const selectedOption = document.getElementById('sawEventSelect').value;
	// addPoster(selectedOption.poster);
	//setPostIdByEventName(selectedOption);
//	console.log(selectedOption);
  document.getElementById('title').value = `[후기] ${selectedOption}`;
});

//날짜에 따른 공연 목록
function findListByDate(date){
	const url = `http://localhost:9080/api/${date}/events`;
	fetch( url,{            //url
		method:'GET',        //http method
		headers:{             //http header
			'Accept':'application/json'
		}
	}).then(res=>res.json())
		.then(res=>{
			console.log(res);
			if(res.header.rtcd == '00'){
				const result =
					res.data.map(e => {
						return `<option>${e}</option>`;
					});
					result.unshift(`<option>==선택==</option>`);
					console.log(result);
				// console.log(result.join(''));
				// const head = 
				document.getElementById('sawEventSelect').innerHTML=result.join('');
//				document.getElementById('sawEvent').innerHTML=result.join('');
			}else{

			}
			return result;
		})
		.catch(err=>console.log(err));
}
//공연정보

//분류자동 선택
const $options = document.querySelectorAll('#pcategory option');
[...$options].find(option=>option.value===category).setAttribute('selected','selected');