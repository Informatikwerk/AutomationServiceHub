import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LibraryRegistryService } from '../../entities/library-registry';
import { LibraryRegistry } from '../../entities/library-registry/library-registry.model';

@Component({
    selector: 'jhi-new-library-modal',
    templateUrl: './new-library-modal.component.html'
})

export class NewLibraryModalComponent {
    formInvalid = false;
    libraryRegistry: LibraryRegistry;

    constructor(public activeModal: NgbActiveModal,
                private libraryRegistryService: LibraryRegistryService) {
        // this.uploadLibraryForm = this.fb.group( {
        //
        // })
    }

    onFileChange(event) {
        const reader = new FileReader();

        // reader.onload = function (e) {
        //     console.log(reader.result);
        // };

        if (event.target.files && event.target.files.length) {
            const files = event.target.files;
            for (const singleFile of files) {
                console.log('file ', singleFile);
            }
            const [file] = event.target.files;
            reader.readAsText(file);
            this.formInvalid = false;
        }
    }

    saveEvent() {
        console.log('save event');
        this.libraryRegistryService.create(this.libraryRegistry);
    }
}
