/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package soupit;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import soupit.handlers.*;

public class SoupitStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new LaunchRequestHandler(),
                        new CancelandStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new HelpIntentHandler(),
                        new FallbackIntentHandler(),
                        new ZutatenAbfrageHandler(),
                        new ZubereitungsWeiterHandler(),
                        new ZubereitungsZurueckHandler(),
                        new ZutatenAusschliessenAbfrageHandler(),
                        new ZutatenAusschliessenHandler(),
                        new ZutatenAuswahlHandler(),
                        new RezeptAuswahlHandler(),
                        new ZutatenWiederaufnahmeHandler(),
                        new ZubereitungStartenHandler(),
                        new PortionenAuswahlHandler(),
                        new ImBatmanHandler(),
                        new YesIntentHandler(),

                        new NoIntentHandler(),
                        new WeitereRezepteHandler(),
                        new ZubereitungAbschliessenHandler())
                .withTableName("soupit")
                .withAutoCreateTable(true)
                .build();
    }

    public SoupitStreamHandler() {
        super(getSkill());
    }

}
