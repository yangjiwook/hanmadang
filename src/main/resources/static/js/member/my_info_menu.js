


//const $hamburger = document.getElementById('hamburger');
//const $hamburgerMenu = document.querySelector('#hamburger .hamburger__menu');
//const $hamburgerTopMenus = document.querySelectorAll('#hamburger .hamburger__menu li');
//
//const clickHamburger_h = e => {
//  console.log('햄버거 클릭');
//  $hamburgerTopMenus.forEach(ele => {
//    ele.classList.toggle('active');
//  });
//}
//$hamburger.addEventListener('click', clickHamburger_h);
//
//const leavePoint_h = e => {
//  console.log('shes gone~');
//  $hamburgerTopMenus.forEach(ele => {
//    ele.classList.toggle('active');
//  });
//}
//$hamburgerMenu.addEventListener('mouseleave', leavePoint_h);


// 내활동내역 메뉴 드롭다운
function openCloseToc() {
  if (document.getElementById('toc-content').style.display === 'block') {
    document.getElementById('toc-content').style.display = 'none';
    document.getElementById('arrow').src ="../img/arrow_down.png";
  } else {
    document.getElementById('toc-content').style.display = 'block';
    document.getElementById('arrow').src ="../img/arrow_up.png";
  }
}