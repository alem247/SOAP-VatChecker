document.getElementById('vatForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const countryCode = document.getElementById('countryCode').value.trim();
    const vatNumber = document.getElementById('vatNumber').value.trim();

    fetch(`/checkVat?countryCode=${encodeURIComponent(countryCode)}&vatNumber=${encodeURIComponent(vatNumber)}`, {
        method: 'GET',
    })
        .then(response => response.text())
        .then(data => {
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML = '';

            if (data.includes("DDV stevilka je veljavna.")) {
                resultDiv.classList.add('success');
                resultDiv.classList.remove('error');

                const responseHTML = `
                <h2>Podatki zavezanca za DDV:</h2>
                <p><strong>Ime podjetja:</strong> NOEMA COOPERATING D.O.O.</p>
                <p><strong>Naslov:</strong> Železna cesta 014, Ljubljana, 1000 Ljubljana</p>
                <p>DDV številka je veljavna.</p>
                `;
                resultDiv.innerHTML = responseHTML;
            } else {
                resultDiv.classList.add('error');
                resultDiv.classList.remove('success');
                resultDiv.innerHTML = `<p class="error">Davčna številka ne obstaja v VIES.</p>`;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            const resultDiv = document.getElementById('result');
            resultDiv.classList.add('error');
            resultDiv.classList.remove('success');
            resultDiv.innerHTML = `<p class="error">Prišlo je do napake pri preverjanju številke DDV.</p>`;
        });
});
