// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
#include "Enums.h"
#include "pch.h"
#include "BackgroundImage.h"

namespace AdaptiveSharedNamespace
{
    std::string BackgroundImage::GetUrl() const { return m_url; }

    void BackgroundImage::SetUrl(const std::string& value) { m_url = value; }

    BackgroundImageMode BackgroundImage::GetMode() const { return m_mode; }

    void BackgroundImage::SetMode(const BackgroundImageMode& value) { m_mode = value; }

    HorizontalAlignment BackgroundImage::GetHorizontalAlignment() const { return m_hAlignment; }

    void BackgroundImage::SetHorizontalAlignment(const HorizontalAlignment& value) { m_hAlignment = value; }

    VerticalAlignment BackgroundImage::GetVerticalAlignment() const { return m_vAlignment; }

    void BackgroundImage::SetVerticalAlignment(const VerticalAlignment& value) { m_vAlignment = value; }

    Json::Value BackgroundImage::SerializeToJsonValue() const
    {
        Json::Value root;

        // if BackgroundImage has a url and the rest as default values
        if (!m_url.empty() && m_mode == BackgroundImageMode::Stretch && m_hAlignment == HorizontalAlignment::Left &&
            m_vAlignment == VerticalAlignment::Top)
        {
            root = m_url;
        }

        // standard serialization process
        else
        {
            if (!m_url.empty())
            {
                root[AdaptiveCardSchemaKeyToString(AdaptiveCardSchemaKey::Url)] = m_url;
            }

            if (m_mode != BackgroundImageMode::Stretch)
            {
                root[AdaptiveCardSchemaKeyToString(AdaptiveCardSchemaKey::Mode)] =
                    BackgroundImageModeToString(m_mode);
            }

            if (m_hAlignment != HorizontalAlignment::Left)
            {
                root[AdaptiveCardSchemaKeyToString(AdaptiveCardSchemaKey::HorizontalAlignment)] =
                    HorizontalAlignmentToString(m_hAlignment);
            }

            if (m_vAlignment != VerticalAlignment::Top)
            {
                root[AdaptiveCardSchemaKeyToString(AdaptiveCardSchemaKey::VerticalAlignment)] =
                    VerticalAlignmentToString(m_vAlignment);
            }
        }
        return root;
    }

    std::shared_ptr<BackgroundImage> BackgroundImage::Deserialize(const Json::Value& json)
    {
        std::shared_ptr<BackgroundImage> image = std::make_shared<BackgroundImage>();

        image->SetUrl(ParseUtil::GetString(json, AdaptiveCardSchemaKey::Url, true));

        image->SetMode(ParseUtil::GetEnumValue<BackgroundImageMode>(
            json, AdaptiveCardSchemaKey::Mode, BackgroundImageMode::Stretch, BackgroundImageModeFromString));

        image->SetHorizontalAlignment(ParseUtil::GetEnumValue<HorizontalAlignment>(
            json, AdaptiveCardSchemaKey::HorizontalAlignment, HorizontalAlignment::Left, HorizontalAlignmentFromString));

        image->SetVerticalAlignment(ParseUtil::GetEnumValue<VerticalAlignment>(
            json, AdaptiveCardSchemaKey::VerticalAlignment, VerticalAlignment::Top, VerticalAlignmentFromString));

        return image;
    }
}