import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutomationServiceHubGuiRegisterModule } from './gui-register/gui-register.module';
import { AutomationServiceHubNodeRegistryModule } from './node-registry/node-registry.module';
import { AutomationServiceHubNodeMetaRegistryModule } from './node-meta-registry/node-meta-registry.module';
import { AutomationServiceHubLibraryRegistryModule } from './library-registry/library-registry.module';
import { AutomationServiceHubSourcesModule } from './sources/sources.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutomationServiceHubGuiRegisterModule,
        AutomationServiceHubNodeRegistryModule,
        AutomationServiceHubNodeMetaRegistryModule,
        AutomationServiceHubLibraryRegistryModule,
        AutomationServiceHubSourcesModule,
        AutomationServiceHubSourcesModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubEntityModule {
}
