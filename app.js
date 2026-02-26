const reloadBtn = document.getElementById('reloadBtn');
const panelFrame = document.getElementById('panelFrame');
const notice = document.getElementById('notice');

reloadBtn.addEventListener('click', () => {
  panelFrame.src = panelFrame.src;
});

panelFrame.addEventListener('load', () => {
  notice.style.display = 'none';
});

window.addEventListener('error', () => {
  notice.style.display = 'block';
});
