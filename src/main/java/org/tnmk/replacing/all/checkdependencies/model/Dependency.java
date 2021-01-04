package org.tnmk.replacing.all.checkdependencies.model;

import java.util.Objects;

public class Dependency {
  private String groupId;
  private String artifactId;
  private String version;
  private String packageType;
//  private String link;
//  private String fileName;

  public Dependency(String groupId, String artifactId, String version, String packageType) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.packageType = packageType;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Dependency that = (Dependency) o;
    return Objects.equals(groupId, that.groupId) &&
        Objects.equals(artifactId, that.artifactId) &&
        Objects.equals(version, that.version) &&
        Objects.equals(packageType, that.packageType);
  }

  @Override public int hashCode() {
    return Objects.hash(groupId, artifactId, version, packageType);
  }

  @Override public String toString() {
    return "Dependency{" +
        "groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", version='" + version + '\'' +
        ", packageType='" + packageType + '\'' +
        '}';
  }

  public String getGroupId()
  {
    return groupId;
  }

  public void setGroupId(String groupId)
  {
    this.groupId = groupId;
  }

  public String getArtifactId()
  {
    return artifactId;
  }

  public void setArtifactId(String artifactId)
  {
    this.artifactId = artifactId;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public String getPackageType() {
    return packageType;
  }

  public void setPackageType(String packageType) {
    this.packageType = packageType;
  }
}
