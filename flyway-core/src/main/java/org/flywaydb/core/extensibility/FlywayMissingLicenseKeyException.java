/*
 * Copyright (C) Red Gate Software Ltd 2010-2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.core.extensibility;

import org.flywaydb.core.api.FlywayException;

public class FlywayMissingLicenseKeyException extends FlywayException {
    public FlywayMissingLicenseKeyException() {
        super("Missing license key. " +
                      "Ensure 'flyway.licenseKey' is set to a valid Flyway license key (\"FL01\" followed by 512 hex chars)");
    }

    public FlywayMissingLicenseKeyException(String featureName, Exception e) {
        super("Missing license key. " +
                      "You need a valid Flyway license key in order to use '" + featureName + "'. " +
                      "Ensure 'flyway.licenseKey' is set to a valid Flyway license key (\"FL01\" followed by 512 hex chars)", e);
    }
}