import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AutomationServiceHubLibraryModule } from './library/library.module';

/* jhipster-needle-add-pageset-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutomationServiceHubLibraryModule,
        /* jhipster-needle-add-pageset-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubPageSetsModule {
}
