const LOCAL_STORAGE_KEY = 'ijug_idp';

function selectIdP(e) {
	const alias = e.value;
	window.localStorage.setItem(LOCAL_STORAGE_KEY, alias);
	selectIdPFromAlias(alias);
}

function selectIdPFromAlias(alias) {
	const option = document.querySelector('option[value="' + alias + '"]');
	const url = option.dataset.url;
	const target = document.getElementById('btnLogin');
	target.href = url;
	if (alias) {
		target.classList.remove('pf-m-disabled');
	} else {
		target.classList.add('pf-m-disabled');
	}
}

function selectIdPFromLocalStorage() {
	const storedAlias = window.localStorage.getItem(LOCAL_STORAGE_KEY);
	if (storedAlias) {
		selectIdPFromAlias(storedAlias);
		const select = document.getElementById('jug-select');
		select.value = storedAlias;
	}
}

document.addEventListener('DOMContentLoaded', selectIdPFromLocalStorage);
