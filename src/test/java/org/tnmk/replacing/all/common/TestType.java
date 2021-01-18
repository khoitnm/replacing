package org.tnmk.replacing.all.common;

import org.junit.jupiter.api.Disabled;

public interface TestType {
  /**
   * This tag indicates that the test case is actually used for triggering an application running.
   * Its main purpose is not just for testing.
   *
   * And the test case inside that class should use {@link Disabled} so that it's only triggered manually.
   */
  String APPLICATION_TRIGGER = "application-trigger";
}
