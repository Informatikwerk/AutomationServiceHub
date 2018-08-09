import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutomationServiceHubLibraryRegistryModule } from './library-registry/library-registry.module';
import { AutomationServiceHubSourcesModule } from './sources/sources.module';

import { AutomationServiceHubNodesModule } from './nodes/nodes.module';
import { AutomationServiceHubGuisModule } from './guis/guis.module';
import { AutomationServiceHubNodesMetaModule } from './nodes-meta/nodes-meta.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutomationServiceHubLibraryRegistryModule,
        AutomationServiceHubSourcesModule,
        AutomationServiceHubSourcesModule,
        AutomationServiceHubNodesModule,
        AutomationServiceHubGuisModule,
        AutomationServiceHubNodesMetaModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubEntityModule {
}
