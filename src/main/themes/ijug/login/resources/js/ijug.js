function selectIdP(e) {
	const target = document.getElementById('btnLogin');
	target.href = e.value;
	if (e.value) {
		target.classList.remove('pf-m-disabled');
	} else {
		target.classList.add('pf-m-disabled');
	}
}
