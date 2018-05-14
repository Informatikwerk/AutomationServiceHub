import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-new-library-modal',
    templateUrl: './new-library-modal.component.html'
})

export class NewLibraryModalComponent {
    formInvalid = true;

    constructor(public activeModal: NgbActiveModal) {
    }

    onFileChange(event) {
        const reader = new FileReader();

        if (event.target.files && event.target.files.length) {
            const [file] = event.target.files;
            reader.readAsDataURL(file);
            this.formInvalid = false;
        }
    }
}
