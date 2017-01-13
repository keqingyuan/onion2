/*
 * Copyright 2015-2016 http://onion.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onion.web.bean.po.system;

public class SystemVersion implements Comparable<SystemVersion> {
    public String name;
    public String comment;
    public String website;
    public int majorVersion    = 1;
    public int minorVersion    = 0;
    public int revisionVersion = 0;
    public boolean snapshot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getRevisionVersion() {
        return revisionVersion;
    }

    public void setRevisionVersion(int revisionVersion) {
        this.revisionVersion = revisionVersion;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    public void setSnapshot(boolean snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public int compareTo(SystemVersion o) {
        if (null == o) return -1;
        if (o.getMajorVersion() > this.getMajorVersion()) return -1;
        if (o.getMajorVersion() == this.getMajorVersion()) {
            if (o.getMinorVersion() > this.getMinorVersion()) return -1;
            if (o.getMinorVersion() == this.getMinorVersion()) {
                if (o.getRevisionVersion() > this.getRevisionVersion()) return -1;
                if (o.getRevisionVersion() == this.getRevisionVersion()) return 0;
                return 1;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        SystemVersion systemVersion = new SystemVersion();
        systemVersion.setMajorVersion(2);
        systemVersion.setMinorVersion(2);
        systemVersion.setRevisionVersion(1);

        SystemVersion systemVersion2 = new SystemVersion();
        systemVersion2.setMajorVersion(3);
        systemVersion2.setMinorVersion(2);
        systemVersion2.setRevisionVersion(1);

        System.out.println(systemVersion.compareTo(systemVersion2));
    }
}
