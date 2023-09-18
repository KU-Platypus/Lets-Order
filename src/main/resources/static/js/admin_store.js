function previewFile() {
    const preview = document.querySelector('#bannerImg');
    const file = document.querySelector('#fileInput').files[0];
    const reader = new FileReader();

    reader.addEventListener("load", function () {
      preview.src = reader.result;
    }, false);

    if (file) {
      reader.readAsDataURL(file);
    }
  }

document.querySelector('form').addEventListener('submit', function(event) {
    alert('적용되었습니다');
});
