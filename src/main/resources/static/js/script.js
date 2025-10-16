/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content */
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var dropdownContent = this.nextElementSibling;
    if (dropdownContent.style.display === "block") {
      dropdownContent.style.display = "none";
    } else {
      dropdownContent.style.display = "block";
    }
  });
}

/* ------------------- */
document.addEventListener("DOMContentLoaded", function() {
  const form = document.querySelector("form");
  const dataNascimentoInput = document.getElementById("idDataNascimento");
  const dataAdmissaoInput = document.getElementById("idDataAdmissao");
  const cnhEmissaoInput = document.getElementById("idCnhDataEmissao");
  const cnhValidadeInput = document.getElementById("idCnhDataValidade");

  /* ------------------- */
  if (form && dataNascimentoInput && dataAdmissaoInput && cnhEmissaoInput && cnhValidadeInput) {
    form.addEventListener("submit", function(event) {
      const dataNascimento = new Date(dataNascimentoInput.value);
      const dataAdmissao = new Date(dataAdmissaoInput.value);
      const hoje = new Date();

      let idade = hoje.getFullYear() - dataNascimento.getFullYear();
      const mes = hoje.getMonth() - dataNascimento.getMonth();
      if (mes < 0 || (mes === 0 && hoje.getDate() < dataNascimento.getDate())) {
        idade--;
      }

      const dataMaioridade = new Date(
        dataNascimento.getFullYear() + 18,
        dataNascimento.getMonth(),
        dataNascimento.getDate()
      );

      if (idade < 18) {
        event.preventDefault();
        alert("O motorista deve ter pelo menos 18 anos.");
        dataNascimentoInput.focus();
        return;
      }

      if (dataAdmissao < dataMaioridade) {
        event.preventDefault();
        alert("A data de admissão deve ser após o motorista completar 18 anos.");
        dataAdmissaoInput.focus();
        return;
      }

      const dataEmissao = new Date(cnhEmissaoInput.value);
      const dataValidade = new Date(cnhValidadeInput.value);

      if (dataValidade < dataEmissao) {
        event.preventDefault();
        alert("A data de validade da CNH não pode ser anterior à data de emissão.");
        cnhValidadeInput.focus();
        return;
      }
    });
  }
});
