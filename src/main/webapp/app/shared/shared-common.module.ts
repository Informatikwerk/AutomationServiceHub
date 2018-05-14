import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';

import {
    AutomationServiceHubSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';
import { NewLibraryModalComponent } from './new-library-modal/new-library-modal.component';

@NgModule({
    imports: [
        AutomationServiceHubSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        NewLibraryModalComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        }
    ],
    exports: [
        AutomationServiceHubSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        NewLibraryModalComponent
    ],
    entryComponents: [
        NewLibraryModalComponent
    ]
})
export class AutomationServiceHubSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
