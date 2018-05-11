import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    GuiRegisterService,
    GuiRegisterPopupService,
    GuiRegisterComponent,
    GuiRegisterDetailComponent,
    GuiRegisterDialogComponent,
    GuiRegisterPopupComponent,
    GuiRegisterDeletePopupComponent,
    GuiRegisterDeleteDialogComponent,
    guiRegisterRoute,
    guiRegisterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...guiRegisterRoute,
    ...guiRegisterPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GuiRegisterComponent,
        GuiRegisterDetailComponent,
        GuiRegisterDialogComponent,
        GuiRegisterDeleteDialogComponent,
        GuiRegisterPopupComponent,
        GuiRegisterDeletePopupComponent,
    ],
    entryComponents: [
        GuiRegisterComponent,
        GuiRegisterDialogComponent,
        GuiRegisterPopupComponent,
        GuiRegisterDeleteDialogComponent,
        GuiRegisterDeletePopupComponent,
    ],
    providers: [
        GuiRegisterService,
        GuiRegisterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubGuiRegisterModule {}
